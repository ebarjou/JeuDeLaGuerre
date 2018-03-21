package ruleEngine;

import game.gameState.GameState;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.rules.masterRules.*;
import ruleEngine.rules.newRules.IRule;

/**
 * Object acting as a hub for {@link game.Game} to check actions on the board. Should be instantiated once.
 * @see IRule
 * @see RuleResult
 */
public class RuleChecker {
    private IRule moveRuleMaster;
    private IRule attackRuleMaster;
    private IRule commRuleMaster;
    private IRule endRuleMaster;
    private IRule victoryRuleMaster;

    public RuleChecker() {
        moveRuleMaster = new MoveRules();
        attackRuleMaster = new AttackRules();
        commRuleMaster = new CommRules();
        endRuleMaster = new EndRules();
        victoryRuleMaster = new VictoryRules();

        /*System.out.println("MoveRule :\n" + moveRuleMaster.toString() + "\n");
        /*System.out.println("AttackRule :\n" + attackRuleMaster.toString() + "\n");
        System.out.println("EndRule :\n" + endRuleMaster.toString() + "\n");
        System.out.println("VictoryRule :\n" + victoryRuleMaster.toString() + "\n");
        System.out.println("CommunicationRule :\n" + commRuleMaster.toString() + "\n");
*/
    }

    private void computeCommunications(GameState gameState){
        commRuleMaster.applyResult(gameState, null, null);
    }

    /**
     * Check if a given action on a given stage of the game is allowed or not.
     * @param gameState The stage of the game when the action is performed.
     * @param action The action to check if allowed or not.
     * @return RuleResult object containing information about the validity of the performed action.
     * @throws IncorrectGameActionException The action is not recognized by the RuleChecker.
     */
    public RuleResult checkAction(GameState gameState, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        IRule mr = null;
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
                    result.addMessage(victoryResult.getLogMessage());
            }
        }

        return result;
    }
}
