package analyse.mapMethods;

import analyse.InfoModule;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.entity.EUnitProperty;

import java.util.Collection;

public abstract class AbstractStaticMapMethod implements IMetricsMapMethod {

    private boolean usingAttack;

    AbstractStaticMapMethod(boolean usingAttack) { this.usingAttack = usingAttack;}

    @Override
    public double[][] compute(GameState state, EPlayer player) {
        //THIS METHOD IS INCORRECT, POSSIBLE MOVES ARE NOT CHECKED
        double[][] result = InfoModule.initializeDoubleMap(state, player);

        Collection<Unit> units = InfoModule.getAllUnitsFromPlayer(state, player);
        for (Unit unit : units) {
            if (!isInCommunication(state, unit))
                continue;

            applyAllAxis(state, result, unit);
        }

        return result;
    }

    private boolean isInCommunication(GameState state, Unit unit) {
        return state.isInCommunication(unit.getPlayer(), unit.getX(), unit.getY());
    }

    private void applyAllAxis(GameState state, double[][] map, Unit unit) {
        EUnitProperty unitProperty = unit.getUnitData();
        int value = (usingAttack ? unitProperty.getAtkValue() : unitProperty.getDefValue());
        int range = unitProperty.getFightRange();

        int x = unit.getX();
        int y = unit.getY();


        /*			0 1 2
         *			3 u 4
         *			5 6 7
         */
        boolean[] axis = {true, true, true, true, true, true, true, true};

        for (int i = 0; i <= range; ++i) {
            axis[0] = applySingleAxis(state, map, value, x - i, y - i, axis[0]);
            axis[1] = applySingleAxis(state, map, value, x, y - i, axis[1]);
            axis[2] = applySingleAxis(state, map, value, x + i, y - i, axis[2]);
            axis[3] = applySingleAxis(state, map, value, x - i, y, axis[3]);
            axis[4] = applySingleAxis(state, map, value, x + i, y, axis[4]);
            axis[5] = applySingleAxis(state, map, value, x - i, y + i, axis[5]);
            axis[6] = applySingleAxis(state, map, value, x, y + i, axis[6]);
            axis[7] = applySingleAxis(state, map, value, x + i, y + i, axis[7]);
        }
    }

    private boolean applySingleAxis(GameState state, double[][] map, int value, int x, int y, boolean previousValue) {
        if (!previousValue)
            return false;

        if (!state.isValidCoordinate(x, y))
            return false;

        if (state.isBuilding(x, y) && !state.getBuildingType(x, y).isAccessible())
            return false;

        map[x][y] += value + (!usingAttack && state.isBuilding(x, y) ? state.getBuildingType(x, y).getBonusDef() : 0);
        return true;
    }
}
