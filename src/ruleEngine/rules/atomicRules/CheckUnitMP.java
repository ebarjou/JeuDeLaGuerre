package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

/**
 * Check if a move performed by a unit is within its range of movement.<br>
 * Valid if the unit has enough movement points to perform the move, invalid otherwise.
 * @see ruleEngine.rules.masterRules.MoveRules
 */
public class CheckUnitMP extends Rule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        try {
            int x = action.getSourceCoordinates().getX();
            int y = action.getSourceCoordinates().getY();
            int x2 = action.getTargetCoordinates().getX();
            int y2 = action.getTargetCoordinates().getY();

            int MP = state.getUnitType(x, y).getMovementValue();
            int dist = state.getDistance(x, y, x2, y2);

            if (dist > MP) {
                result.addMessage(this,
                        "Not enough movement point, the unit has "
                                + MP + " MP, and you need " + dist + " MP");
                result.invalidate();
                return false;
            }
            return true;
        } catch (NullPointerException e){
            result.addMessage(this, "Not enough movement point");
            result.invalidate();
            return false;
        }
    }
}
