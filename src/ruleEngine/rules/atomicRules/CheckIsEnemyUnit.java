package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if the targeted unit from an attack is an enemy one.<br>
 * Valid if the targeted unit is an enemy, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckIsEnemyUnit implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        int x = action.getTargetCoordinates().getX();
        int y = action.getTargetCoordinates().getY();

        if (state.isUnit(x, y)) {
            if (state.getActualPlayer() == state.getUnitPlayer(x, y)) {
                result.addMessage(this, "Targeted unit is not an enemy.");
                result.invalidate();
                return false;
            }
            return true;
        }
        //TODO: Code repetition here, use rule dependencies instead (-> CheckIsAllyUnit)
        result.addMessage(this, "There is no unit at (" + x + ";" + y + ")");
        result.invalidate();
        return false;
    }



    public String toString(){
        return this.getClass().getSimpleName();
    }
}
