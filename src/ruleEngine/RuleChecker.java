package ruleEngine;

import game.board.Board;
import game.gameState.GameState;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.rules.masterRules.*;

public class RuleChecker {
    private MasterRule moveRuleMaster;
    private MasterRule attackRuleMaster;
    private MasterRule commRuleMaster;
    private MasterRule endRuleMaster;

    public RuleChecker() {
        moveRuleMaster = new MoveRules();
        attackRuleMaster = new AttackRules();
        commRuleMaster = new CommRules();
        endRuleMaster = new EndRules();
    }

    public void computeCommunications(GameState gameState){
        commRuleMaster.applyResult(gameState, null, null);
    }

    public RuleResult checkAction(GameState gameState, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        MasterRule mr = null;
        switch (action.getActionType()) {
            case MOVE:
                mr = moveRuleMaster;
                break;
            case ATTACK:
                mr = attackRuleMaster;
                break;
            case END_TURN:
                mr = endRuleMaster;
                break;
            default:
                throw new IncorrectGameActionException("Unhandled GameAction type.");
        }

        mr.checkAction(gameState, action, result);

        if (result.isValid())
            mr.applyResult(gameState, action, result);

        return result;
    }
}
