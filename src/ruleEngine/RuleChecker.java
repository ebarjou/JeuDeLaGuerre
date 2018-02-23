package ruleEngine;

import game.Game;
import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.rules.MasterRule;
import ruleEngine.rules.MoveRules;

public class RuleChecker {
    private static RuleChecker instance;

    private MasterRule moveRuleMaster;

    private RuleChecker() {
        moveRuleMaster = MoveRules.getInstance();
    }

    public static RuleChecker getInstance() {
        if (instance == null)
            instance = new RuleChecker();

        return instance;
    }

    public RuleResult checkAction(Board board, GameState gameState, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        MasterRule mr = null;
        switch (action.getActionType()) {
            case MOVE:
                mr = moveRuleMaster;
                break;
            default:
                throw new IncorrectGameActionException("Unhandled GameAction type.");
        }

        mr.checkAction(board, gameState, action, result);

        if (result.isValid())
            mr.applyResult(board, gameState, action, result);

        return result;
    }
}
