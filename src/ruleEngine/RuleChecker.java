package ruleEngine;

import game.gameState.GameState;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.rules.masterRules.*;

public class RuleChecker {
    private MasterRule moveRuleMaster;
    private MasterRule attackRuleMaster;
    private MasterRule commRuleMaster;
    private MasterRule endRuleMaster;
    private MasterRule victoryRuleMaster;

    public RuleChecker() {
        moveRuleMaster = new MoveRules();
        attackRuleMaster = new AttackRules();
        commRuleMaster = new CommRules();
        endRuleMaster = new EndRules();
        victoryRuleMaster = new VictoryRules();
    }

    private void computeCommunications(GameState gameState){
        commRuleMaster.applyResult(gameState, null, null);
    }

    public RuleResult checkAction(GameState gameState, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        MasterRule mr = null;
        boolean checkVictory = false;
        switch (action.getActionType()) {
            case MOVE:
                mr = moveRuleMaster;
                checkVictory = true;
                break;
            case ATTACK:
                mr = attackRuleMaster;
                checkVictory = true;
                break;
            case END_TURN:
                mr = endRuleMaster;
                break;
            case COMMUNICATION:
                computeCommunications(gameState);
                return result;
            default:
                throw new IncorrectGameActionException("Unhandled GameAction type.");
        }

        mr.checkAction(gameState, action, result);

        if (result.isValid()) {
            mr.applyResult(gameState, action, result);
            computeCommunications(gameState);
            //Check victory if necessary
            if(checkVictory) {
                RuleResult victoryResult = new RuleResult();
                victoryRuleMaster.checkAction(gameState, action, victoryResult);
                if (!victoryResult.getLogMessage().isEmpty())
                    result.addMessage(victoryRuleMaster, victoryResult.getLogMessage());
            }
        }

        return result;
    }
}
