package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
* Check if a unit is owned by the player requesting the move.<br>
* Valid if it's the same player, invalid otherwise.
*
* @see ruleEngine.rules.masterRules.MoveRules
* @see ruleEngine.rules.masterRules.AttackRules
*/
public class CheckIsAllyUnit implements IRule {



    public String toString(){
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();

        if (state.isUnit(x, y)) {
            if (state.getActualPlayer() != state.getUnitPlayer(x, y)) {
                result.addMessage(this, "This unit is not owned by " + state.getActualPlayer().toString() + ".");
                result.invalidate();
                return false;
            }

            return true;
        }
        //TODO: Code repetition here, use rule dependencies instead (-> CheckIsEnemyUnit)
        result.addMessage(this, "There is no unit at (" + x + ", " + y + ").");
        result.invalidate();
        return false;
    }
}
