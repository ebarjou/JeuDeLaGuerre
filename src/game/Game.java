package game;

import game.gameState.GameState;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import system.BadFileFormatException;
import system.LoadFile;
import ui.GameResponse;
import player.Player;
import ui.UIAction;

import java.io.IOException;
import java.util.Stack;

import static ui.commands.GameToUserCall.*;

public class Game {
    private static Game instance = null;
    private GameState gameState;
    private Stack<GameState> historyGameState;
    private final Player player1;
    private final Player player2;
    private final RuleChecker ruleChecker;

    public static Game getInstance(){
        return instance;
    }

    public static void init(Player player1, Player player2){
        instance = new Game(player1, player2);
    }

    private Game(Player player1, Player player2) {
        gameState = new GameState(25, 20); // Not sure if we should let 25 / 20 like this
        this.player1 = player1;
        this.player2 = player2;
        historyGameState = new Stack<>();

        ruleChecker = new RuleChecker();
    }

    public void start(){
        handleGameAction(new GameAction(EPlayer.values()[0], EGameActionType.COMMUNICATION));

        while(true){
            Player player = getPlayer();
            UIAction action = player.getCommand();
            GameResponse response = processCommand(action);
            player.setResponse(response);
        }
    }

    public Player getPlayer() {
        if (gameState.getActualPlayer() == EPlayer.PLAYER_NORTH)
            return player1;
        return player2;
    }

    public GameState getGameState(){
        return gameState;
    }

    public void reinit(GameState gameState){
        this.gameState = gameState;
        this.historyGameState = new Stack<>();
    }

    private GameResponse handleGameAction(GameAction action) {
        GameState actualGameState = this.gameState.clone(); // copy of the GameState & Board before attempting the action
        try {
            RuleResult res = ruleChecker.checkAndApplyAction(gameState, action);
            if (res.isValid()) {
                historyGameState.push(actualGameState);
                //Communication
                //ruleChecker.computeCommunications(gameState);
                return new GameResponse(VALID, res.getLogMessage(), gameState, gameState.getActualPlayer());
            } else {
                return new GameResponse(INVALID, res.getLogMessage(),gameState, gameState.getActualPlayer());
            }
        } catch (Exception e) {
            return new GameResponse(GAME_ERROR, null, gameState, gameState.getActualPlayer());
        }
    }

    public GameResponse processCommand(UIAction cmd) {
        if(cmd == null)  new GameResponse(GAME_ERROR, "Error : null call.", gameState, gameState.getActualPlayer());
        assert cmd != null;
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                break;
            }
            case LOAD: {
                LoadFile lf = new LoadFile();
                try {
                    lf.loadFile(cmd.getText());
                    GameResponse gameResponse = handleGameAction(cmd.getGameAction(gameState.getActualPlayer()));
                    historyGameState = new Stack<>();
                    return gameResponse;
                } catch (IOException e) {
                    return new GameResponse(INVALID, e.getMessage(), gameState, gameState.getActualPlayer());
                } catch (BadFileFormatException e){
                    return new GameResponse(INVALID, e.toString(), gameState, gameState.getActualPlayer());
                }
            }
            case SAVE: {
                LoadFile lf = new LoadFile();
                try{
                    lf.save(cmd.getText(), gameState);
                } catch (IOException e) {
                    return new GameResponse(INVALID, e.getMessage(), gameState, gameState.getActualPlayer());
                }
                break;
            }
            case REVERT: {
                if(!historyGameState.isEmpty())
                    this.gameState = historyGameState.pop();
                return new GameResponse(VALID, cmd.getErrorMessage(), gameState, gameState.getActualPlayer());
            }
            case HELP:{
                String commands = "Commands available (type ENTER to execute a command) :\n- To move a unit, type : move A1 A2\n" +
                                    "  Or you can type the same command without move, like : A1 A2\n" +
                                    "  Where A1 and A2 are two positions on the board (please respect this format [LetterNumber]).\n" +
                                    "  By clicking on a cell, it puts directly in the text bar the associated coordinates.\n" +
                                    "- To attack a unit, type : attack A1 A2\n" +
                                    "- To end your turn, type : end\n" +
                                    "- To undo your last action, type : revert\n" +
                                    "- To save the actual state of the game, type : save filename\n" +
                                    "- To load a saved game generated by the previous command, type : load filename\n" +
                                    "\n- If you want to play with a single bot, you have to add the option -bot before the program execution.\n" +
                                    "- If you want to play with two bots, you have to add the option -bot2\n";
                return new GameResponse(VALID, commands, gameState, gameState.getActualPlayer());
            }
            case CMD_ERROR: {
                return new GameResponse(INVALID, cmd.getErrorMessage(), gameState, gameState.getActualPlayer());
            }
            case GAME_ACTION: {
                return handleGameAction(cmd.getGameAction(gameState.getActualPlayer()));
            }
            case LIST_UNIT: {
                break;
            }
        }
        return new GameResponse(GAME_ERROR, "Error : Unimplemented call.", gameState, gameState.getActualPlayer());
    }
}
