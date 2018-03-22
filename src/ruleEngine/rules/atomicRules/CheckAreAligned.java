package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if a unit is aligned with its target for an attack, this alignment is checked in the 8 directions from the
 * source unit.<br>
 * Valid if there's an alignment, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckAreAligned implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();

        int diffX = Math.abs(src.getX() - dst.getX());
        int diffY = Math.abs(src.getY() - dst.getY());


        if ( ( diffX != diffY ) && (diffX != 0) && (diffY != 0) ) {
            result.addMessage(this,
                    "The source is not aligned either horizontally, vertically or diagonally with the target.");
            result.invalidate();
            return false;
        }
        return true;
    }

    public String toString(){
        return this.getClass().getSimpleName();
    }
}
