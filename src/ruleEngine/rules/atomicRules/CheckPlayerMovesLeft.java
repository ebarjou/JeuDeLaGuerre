package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if the player performing the action has at least one move remaining for this turn.<br>
 * Valid if there is at least one move left, invalid otherwise.
 * @see ruleEngine.rules.masterRules.MoveRules
 */
public class CheckPlayerMovesLeft implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if (state.getActionLeft() < 1) {
            result.invalidate();
            result.addMessage(this, "This player has no action left this turn.");
            return false;
        }

        return true;
    }
}
