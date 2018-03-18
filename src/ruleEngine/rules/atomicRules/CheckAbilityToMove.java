package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

/**
 * Check if a unit is supplied in communication or if is a Relay type unit.<br>
 * Valid if it is, invalid if otherwise.
 *
 * @see EUnitData
 * @see ruleEngine.rules.masterRules.CommRules
 */
public class CheckAbilityToMove extends Rule {

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
}
