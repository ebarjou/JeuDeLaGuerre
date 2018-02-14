package game;

import game.board.BoardManager;
import game.board.IBoardManager;
import game.gameMaster.GameMaster;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import ui.GameResponse;
import ui.UIAction;

import static ui.commands.GameToUserCall.*;

public class Game {
    private static Game instance;
    private IBoardManager boardManager;
    private GameMaster gameMaster; //gamestate...

    private Game() {
        boardManager = BoardManager.getInstance();
        gameMaster = GameMaster.getInstance();
    }

    public static Game getInstance() {
        if (instance == null) instance = new Game();
        return instance;
    }

    private GameResponse handleGameAction(UIAction cmd){
        try {
            GameAction action = cmd.getGameAction(GameMaster.getInstance().getActualState().getActualPlayer());
            RuleResult res = RuleChecker.getInstance().checkAction(boardManager.getBoard(), action);
            if (res.isValid()) {
                boardManager.moveUnit(action.getSourceCoordinates().getX(),
                        action.getSourceCoordinates().getY(),
                        action.getTargetCoordinates().getX(),
                        action.getTargetCoordinates().getY());
                gameMaster.removeAction();

                return new GameResponse(VALID, null);
            } else {
                return new GameResponse(INVALID, res.getLogMessage());
            }
        } catch (Exception e) {
            return new GameResponse(GAME_ERROR, null);
        }
    }

    public GameResponse processCommand(UIAction cmd) {
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                break;
            }
            case LOAD: {
                break;
            }
            case SAVE: {
                break;
            }
            case REVERT: {
                boardManager.revert();
                gameMaster.revert();
                break;
            }
            case END_TURN: {
                boardManager.clearHistory();
                gameMaster.switchPlayer();
                break;
            }
            case CMD_ERROR: {
                return new GameResponse(INVALID, cmd.getErrorMessage());
            }
            case GAME_ACTION: {
                return handleGameAction(cmd);
            }
            case LIST_UNIT: {
                break;
            }
        }
        return new GameResponse(GAME_ERROR, "Error : Unimplemented call.");
    }

}
