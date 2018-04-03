package ruleEngine;

import game.gameState.GameState;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.rules.masterRules.*;
import ruleEngine.rules.newRules.IRule;
import ruleEngine.rules.newRules.RuleComposite;

/**
 * Object acting as a hub for {@link game.Game} to check actions on the board. Should be instantiated once.
 *
 * @see IRule
 * @see RuleResult
 */
public class RuleChecker {
    private final RuleComposite moveRuleMaster;
    private final RuleComposite attackRuleMaster;
    private final RuleComposite commRuleMaster;
    private final RuleComposite endRuleMaster;
    private final RuleComposite victoryRuleMaster;

    public RuleChecker() {
        moveRuleMaster = new MoveRules();
        attackRuleMaster = new AttackRules();
        commRuleMaster = new CommRules();
        endRuleMaster = new EndRules();
        victoryRuleMaster = new VictoryRules();
/*
        System.out.println("MoveRule :\n" + moveRuleMaster.getName() + "\n");
        System.out.println("AttackRule :\n" + attackRuleMaster.getName() + "\n");
        System.out.println("EndRule :\n" + endRuleMaster.getName() + "\n");
        System.out.println("VictoryRule :\n" + victoryRuleMaster.getName() + "\n");
        System.out.println("CommunicationRule :\n" + commRuleMaster.getName() + "\n");
        */
    }

    private void computeCommunications(GameState gameState) {
        commRuleMaster.applyResult(gameState, null, null);
    }

    public boolean checkAction(GameState state, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        IRule masterRule;
        switch (action.getActionType()) {
            case MOVE:
                masterRule = moveRuleMaster;
                break;
            case ATTACK:
                masterRule = attackRuleMaster;
                break;
            default:
                throw new IncorrectGameActionException("Unhandled GameAction type.");
        }
        boolean valid = masterRule.checkAction(state, action, result);
        return valid && result.isValid();
    }

    /**
     * Check if a given action on a given stage of the game is allowed or not.
     *
     * @param gameState The stage of the game when the action is performed.
     * @param action    The action to check if allowed or not.
     * @return RuleResult object containing information about the validity of the performed action.
     * @throws IncorrectGameActionException The action is not recognized by the RuleChecker.
     */
    public RuleResult checkAndApplyAction(GameState gameState, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        RuleComposite mr;
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
            if (checkVictory) {
                RuleResult victoryResult = new RuleResult();
                victoryRuleMaster.checkAction(gameState, action, victoryResult);
                if (!victoryResult.getLogMessage().isEmpty())
                    result.addMessage(victoryResult.getLogMessage());
            }
        }

        return result;
    }
}
