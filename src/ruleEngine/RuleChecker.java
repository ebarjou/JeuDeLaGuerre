package ruleEngine;

import game.board.Board;
import game.gameMaster.GameState;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.rules.masterRules.CommRules;
import ruleEngine.rules.masterRules.MasterRule;
import ruleEngine.rules.masterRules.MoveRules;

public class RuleChecker {
    private static RuleChecker instance;

    private MasterRule moveRuleMaster;
    private MasterRule commRuleMaster;

    private RuleChecker() {
        moveRuleMaster = MoveRules.getInstance();
        commRuleMaster = CommRules.getInstance();
    }

    public static RuleChecker getInstance() {
        if (instance == null)
            instance = new RuleChecker();

        return instance;
    }

    public void computeCommunications(Board board, GameState gameState){
        commRuleMaster.applyResult(board, gameState, null, null);
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
