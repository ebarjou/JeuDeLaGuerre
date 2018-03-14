package game;

import game.board.*;
import game.gameState.GameState;
import game.gameState.IGameState;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import ruleEngine.exceptions.IncorrectGameActionException;
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
    private RuleChecker ruleChecker;

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
        try {
            ruleChecker.checkAction(gameState, new GameAction(EPlayer.values()[0], EGameActionType.COMMUNICATION));
        } catch (ExceptionInInitializerError|IncorrectGameActionException ignore) {}

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

    public IGameState getGameState(){
        return gameState;
    }

    public GameState getGameStateManager() {
        return gameState;
    }

    public void reinit(GameState gameState){
        this.gameState = gameState;
    }

    private GameResponse handleGameAction(UIAction cmd) {
        GameState actualGameState = this.gameState.clone(); // copy of the GameState & Board before attempting the action
        try {
            GameAction action = cmd.getGameAction(gameState.getActualPlayer());
            RuleResult res = ruleChecker.checkAction(gameState, action);
            if (res.isValid()) {
                historyGameState.push(actualGameState);
                //Communication
                //ruleChecker.computeCommunications(gameState);
                return new GameResponse(VALID, null, gameState, gameState.getActualPlayer());
            } else {
                return new GameResponse(INVALID, res.getLogMessage(),gameState, gameState.getActualPlayer());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new GameResponse(GAME_ERROR, null, gameState, gameState.getActualPlayer());
        }
    }

    public GameResponse processCommand(UIAction cmd) {
        if(cmd == null)  new GameResponse(GAME_ERROR, "Error : null call.", gameState, gameState.getActualPlayer());
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                break;
            }
            case LOAD: {
                LoadFile lf = new LoadFile();
                try {
                    lf.loadFile(cmd.getText());
                    return handleGameAction(cmd);
                } catch (IOException e) {
                    return new GameResponse(INVALID, e.getMessage(), gameState, gameState.getActualPlayer());
                }
            }
            case SAVE: {
                break;
            }
            case REVERT: {
                if(!historyGameState.isEmpty())
                    this.gameState = historyGameState.pop();
                return new GameResponse(VALID, cmd.getErrorMessage(), gameState, gameState.getActualPlayer());
            }
            case CMD_ERROR: {
                return new GameResponse(INVALID, cmd.getErrorMessage(), gameState, gameState.getActualPlayer());
            }
            case GAME_ACTION: {
                return handleGameAction(cmd);
            }
            case LIST_UNIT: {
                break;
            }
        }
        return new GameResponse(GAME_ERROR, "Error : Unimplemented call.", gameState, gameState.getActualPlayer());
    }
}
