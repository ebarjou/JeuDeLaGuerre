package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if an attack performed by a unit is able to reach its destination, meaning there is no obstacle between the unit and the targeted cell.<br>
 * Valid if the path is clear, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckIsEmptyAttackPath implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();

        int dirX = dst.getX() - src.getX();
        int dirY = dst.getY() - src.getY();
        if (dirX != 0) dirX /= Math.abs(dirX); // 1 or -1
        if (dirY != 0) dirY /= Math.abs(dirY); // 1 or -1

        int x = src.getX();
        int y = src.getY();
        while (x != dst.getX() || y != dst.getY()) {
            if (state.isBuilding(x, y)
                    && !state.getBuildingType(x, y).isAccessible()) {
                result.addMessage(this,
                        "This unit cannot attack here because there is an obstacle.");
                result.invalidate();
                return false;
            }
            x += dirX;
            y += dirY;
        }
        return true;
    }
}
