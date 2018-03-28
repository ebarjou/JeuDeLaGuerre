package ruleEngine.rules.atomicRules;

import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if a move performed by a unit is within its range of movement.<br>
 * Valid if the unit has enough movement points to perform the move, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.MoveRules
 */
public class CheckUnitMP implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        try {
            int x1 = action.getSourceCoordinates().getX();
            int y1 = action.getSourceCoordinates().getY();
            int x2 = action.getTargetCoordinates().getX();
            int y2 = action.getTargetCoordinates().getY();

            int MP = state.getUnitType(x1, y1).getMovementValue();
            int dist = state.getDistance(x1, y1, x2, y2);

            if (dist > MP) {
                result.addMessage(this,
                        "Not enough movement point, the unit has "
                                + MP + " MP, and you need " + dist + " MP.");
                result.invalidate();
                return false;
            }
            return true;
        } catch (IllegalBoardCallException e) {
            result.addMessage(this, "Not enough movement point.");
            result.invalidate();
            return false;
        }
    }
}
