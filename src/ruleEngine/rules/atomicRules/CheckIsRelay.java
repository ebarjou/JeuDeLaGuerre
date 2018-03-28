package ruleEngine.rules.atomicRules;

import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitProperty;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if a unit is a Relay type unit.<br>
 * Valid if it is, invalid if otherwise.
 *
 * @see EUnitProperty
 * @see ruleEngine.rules.masterRules.CommRules
 */
public class CheckIsRelay implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        try {
            EUnitProperty unitProperty = state.getUnitType(src.getX(), src.getY());
            if (unitProperty.isRelayCommunication())
                return true;
        } catch (IllegalBoardCallException ignored) {
        }

        result.addMessage(this, "This unit is not a relay.");
        result.invalidate();
        return false;
    }
}
