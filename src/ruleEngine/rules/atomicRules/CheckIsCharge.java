package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.rules.newRules.IRule;

import java.util.List;

/**
 * Check if an attack performed by a unit could be a charge.<br>
 * Valid if the target cell could charge, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckIsCharge implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();

        if (!state.getUnitType(src.getX(), src.getY()).isCanCharge()) {
            result.addMessage(this,
                    "The initiating unit is not able to charge.");
            result.invalidate();
            return false;
        }

        if (state.isBuilding(dst.getX(), dst.getY()) &&
                ((state.getBuildingType(dst.getX(), dst.getY()) == EBuildingProperty.FORTRESS) ||
                        (state.getBuildingType(dst.getX(), dst.getY()) == EBuildingProperty.PASS))) {
            result.addMessage(this,
                    "The targeted unit is in a pass or a fortress and cannot be charged.");
            result.invalidate();
            return false;
        }

        int dirX = dst.getX() - src.getX();
        int dirY = dst.getY() - src.getY();
        int diffX = Math.abs(dirX);
        int diffY = Math.abs(dirY);
        if ((diffX != diffY) && (dirX != 0) && (dirY != 0)) return false;

        if (diffX != 0) dirX = dirX / diffX; // 1 or -1
        if (diffY != 0) dirY = dirY / diffY; // 1 or -1

        int x = src.getX();
        int y = src.getY();
        while (x != dst.getX() || y != dst.getY()) {
            if (!state.isUnit(x, y)
                    || !state.getUnitType(x, y).isCanCharge()
                    || !(state.getUnitPlayer(x, y) == action.getPlayer())
                    || isCantAttackUnit(state, new Coordinates(x, y))
                    || (state.isBuilding(x, y) && state.getBuildingType(x, y) == EBuildingProperty.FORTRESS)) {
                result.addMessage(this,
                        "The initiating unit is not in a position to proceed a charge.");
                result.invalidate();
                return false;
            }
            x += dirX;
            y += dirY;
        }
        return true;
    }


    private boolean isCantAttackUnit(GameState state, Coordinates coords) {
        List<Unit> cantAttackUnits = state.getCantAttackUnits();
        for (Unit unit : cantAttackUnits)
            if (unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return true;
        return false;
    }

}
