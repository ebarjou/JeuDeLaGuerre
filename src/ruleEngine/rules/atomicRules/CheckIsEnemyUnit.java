package ruleEngine.rules.atomicRules;

import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckIsEnemyUnit extends Rule {

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
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
}
