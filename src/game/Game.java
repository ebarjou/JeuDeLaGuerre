package game;

import game.board.BoardManager;
import game.board.IBoardManager;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import game.gameMaster.GameMaster;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import ui.CommandException;
import ui.SharedCommand;

import static ui.commands.GameToUserCall.*;

public class Game {
    private static Game instance;
    private IBoardManager boardManager;

    public static Game getInstance(){
        if(instance == null) instance = new Game();
        return instance;
    }

    private Game() {
        boardManager = BoardManager.getInstance();
    }

    public SharedCommand processCommand(SharedCommand cmd) {
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                cmd.setResponse(VALID);
                break;
            }
            case LOAD: {
                break;
            }
            case SAVE: {
                break;
            }
            case REVERT: {
                break;
            }
            case CMD_ERROR: {
                cmd.setResponse(GAME_ERROR);
                return cmd;
            }
            case PAT: {
                break;
            }
            case SURRENDER: {
                break;
            }
            default: {
                try {
                    int[] a = cmd.getCoords1();
                    int[] b = cmd.getCoords2();
                    //System.out.println("GAME : From [" + a[0] + "," + a[1] + "] To [" + b[0] + "," + b[1] + "]");
                    RuleResult res = RuleChecker.getInstance().checkAction(boardManager.getBoard(), convert(cmd));
                    if(res.getLogMessage().isEmpty()){
                        cmd.setResponse(VALID);
                        boardManager.moveUnit(cmd.getCoords1()[0], cmd.getCoords1()[1], cmd.getCoords2()[0], cmd.getCoords2()[1]);
                    } else {
                        cmd.setResponse(INVALID);
                        cmd.setMessage(res.getLogMessage());
                    }
                } catch (Exception e) {
                    cmd.setResponse(GAME_ERROR);
                }
                break;
            }
        }
        return cmd;
    }

    //Wrapper for SharedCommand -> GameAction while not sure about final design layout
    private GameAction convert(SharedCommand cmd) {
        EGameActionType actionType = EGameActionType.NONE;
        switch (cmd.getCommand()) {

            case MOVE:
                actionType = EGameActionType.MOVE;
                break;
            case ATTACK:
                actionType = EGameActionType.ATTACK;
                break;
            case CHARGE:
                actionType = EGameActionType.CHARGE;
                break;
            case PAT:
                actionType = EGameActionType.PROPOSE_DRAW;
                break;
            default:
                return null;
        }

        GameAction result = new GameAction(GameMaster.getInstance().getActualState().getActualPlayer(), actionType);
        try {
            result.setSourceCoordinates(cmd.getCoords1()[0], cmd.getCoords1()[1]);
        } catch (CommandException e) {
            result.setSourceCoordinates(1, 1);
        }

        try {
            result.setTargetCoordinates(cmd.getCoords2()[0], cmd.getCoords2()[1]);
        } catch (CommandException e) {
            result.setTargetCoordinates(1, 1);
        }

        return result;
    }

}
