package ruleEngine.rules.atomicRules;

import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckIsAllyUnit extends Rule {

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();

        if (state.isUnit(x, y)) {
            if (state.getActualPlayer() != state.getUnitPlayer(x, y)) {
                result.addMessage(this, "This unit is not owned by " + state.getActualPlayer().name() + ".");
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
