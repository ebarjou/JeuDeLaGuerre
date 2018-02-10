package ruleEngine;

import game.board.Board;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.gameMaster.GameMaster;
import ruleEngine.rules.MoveRules;

public class RuleChecker {
    private static RuleChecker instance;

    private IRule moveRuleMaster;

    private RuleChecker(){
        moveRuleMaster = MoveRules.getInstance();
    }

    public static RuleChecker getInstance() {
        if (instance == null)
            instance = new RuleChecker();

        return instance;
    }

    public RuleResult checkAction(Board board, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        switch (action.getActionType()) {
            case MOVE:
                moveRuleMaster.checkAction(board, GameMaster.getInstance().getActualState(), action, result);
                break;
            default:
                throw new IncorrectGameActionException("Unhandled GameAction type.");

        }

        return result;
    }
}
