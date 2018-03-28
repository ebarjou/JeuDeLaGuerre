package ruleEngine.rules.atomicRules;

import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if an attack performed by a unit is within its range of action.<br>
 * Valid if the target cell is within the range of the unit, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckUnitRange implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        try {
            int x1 = action.getSourceCoordinates().getX();
            int y1 = action.getSourceCoordinates().getY();
            int x2 = action.getTargetCoordinates().getX();
            int y2 = action.getTargetCoordinates().getY();

            int range = state.getUnitType(x1, y1).getFightRange();
            int dist = state.getDistance(x1, y1, x2, y2);

            if (dist > range) {
                result.addMessage(this,
                        "Not enough range to attack, the unit has a range of "
                                + range + ", and you need a range of " + dist + ".");
                result.invalidate();
                return false;
            }
            return true;
        } catch (NullPointerException | IllegalBoardCallException e) {
            result.addMessage(this, "Not enough range to attack.");
            result.invalidate();
            return false;
        }
    }
}
