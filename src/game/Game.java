package game;

import game.board.*;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
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
    private Player player1;
    private Player player2;

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
    }

    public void start(){
        RuleChecker.getInstance().computeCommunications(gameState.getMutableBoard(), gameState);
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

    public IBoard getBoard(){
        return gameState.getBoard();
    }

    public IGameState getGameState(){
        return gameState;
    }

    public GameState getGameStateManager() {
        return gameState;
    }

    public void reinit(GameState gameState){
        this.gameState = gameState;
        RuleChecker.getInstance().computeCommunications(gameState.getMutableBoard(), gameState);
    }

    private GameResponse handleGameAction(UIAction cmd) {
        GameState actualGameState = this.gameState.clone(); // copy of the GameState & Board before attempting the action
        Board board = gameState.getMutableBoard();
        try {
            GameAction action = cmd.getGameAction(gameState.getActualPlayer());
            RuleResult res = RuleChecker.getInstance().checkAction(board, gameState, action);
            if (res.isValid()) {
                historyGameState.push(actualGameState);
                //Communication
                RuleChecker.getInstance().computeCommunications(gameState.getMutableBoard(), gameState);
                return new GameResponse(VALID, null, board, gameState.getActualPlayer());
            } else {
                return new GameResponse(INVALID, res.getLogMessage(), board, gameState.getActualPlayer());
            }
        } catch (Exception e) {
            return new GameResponse(GAME_ERROR, null, board, gameState.getActualPlayer());
        }
    }

    public GameResponse processCommand(UIAction cmd) {
        if(cmd == null)  new GameResponse(GAME_ERROR, "Error : null call.", gameState.getBoard(), gameState.getActualPlayer());
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                break;
            }
            case LOAD: {
                LoadFile lf = new LoadFile();
                try {
                    lf.loadFile(cmd.getText());
                    RuleChecker.getInstance().computeCommunications(gameState.getMutableBoard(), gameState);
                } catch (IOException e) {
                    return new GameResponse(INVALID, e.getMessage(), gameState.getBoard(), gameState.getActualPlayer());
                }
                return new GameResponse(VALID, null, gameState.getBoard(), gameState.getActualPlayer());
            }
            case SAVE: {
                break;
            }
            case REVERT: {
                if(!historyGameState.isEmpty())
                    this.gameState = historyGameState.pop();
                return new GameResponse(VALID, cmd.getErrorMessage(), gameState.getBoard(), gameState.getActualPlayer());
            }
            case END_TURN: {
                System.out.println("END TURN");
                //Clear History ?
                historyGameState.push(gameState.clone());
                gameState.switchPlayer();
                return new GameResponse(VALID, cmd.getErrorMessage(), gameState.getBoard(), gameState.getActualPlayer());
            }
            case CMD_ERROR: {
                return new GameResponse(INVALID, cmd.getErrorMessage(), gameState.getBoard(), gameState.getActualPlayer());
            }
            case GAME_ACTION: {
                return handleGameAction(cmd);
            }
            case LIST_UNIT: {
                break;
            }
        }
        return new GameResponse(GAME_ERROR, "Error : Unimplemented call.", gameState.getBoard(), gameState.getActualPlayer());
    }
}
