package ruleEngine.rules.atomicRules;

import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckUnitMP extends Rule {

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
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
