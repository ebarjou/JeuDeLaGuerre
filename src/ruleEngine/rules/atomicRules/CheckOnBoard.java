package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if an action is performing within the board boundaries.<br>
 * Valid if the action is performing within the boundaries, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.MoveRules
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckOnBoard implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if (!state.isValidCoordinate(action.getSourceCoordinates().getX(), action.getSourceCoordinates().getY())) {
            result.addMessage(this, "Source coordinates are beyond the board's boundaries.");
            result.invalidate();
            return false;
        }

        if (!state.isValidCoordinate(action.getTargetCoordinates().getX(), action.getTargetCoordinates().getY())) {
            result.addMessage(this, "Target coordinates are beyond the board's boundaries.");
            result.invalidate();
            return false;
        }

        return true;
    }

}
