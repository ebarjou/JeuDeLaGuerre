package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.newRules.IRule;

public class CheckAbilityToMove implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        try {
            EUnitData unitData = state.getUnitType(src.getX(), src.getY());
            if (unitData.isRelayCommunication() || state.isInCommunication(action.getPlayer(), src.getX(), src.getY())) {
                return true;
            }

        } catch (NullPointerException ignored){
        }

        result.addMessage(this, "This unit is not in communication and cannot be used.");
        result.invalidate();
        return false;
    }

    public String toString(){
        return this.getClass().getSimpleName();
    }
}
