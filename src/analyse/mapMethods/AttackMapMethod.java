package analyse.mapMethods;

import analyse.InfoModule;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AttackMapMethod implements IMetricsMapMethod {

    @Override
    public double[][] compute(GameState state, EPlayer player) {
    	//THIS METHOD IS INCORRECT, POSSIBLE MOVES ARE NOT CHECKED
        double[][] result = new double[state.getWidth()][state.getHeight()];
        Collection<Unit> units = InfoModule.getAllUnitsFromPlayer(state, player);
        for(Unit u : units){
        	if (!isInCommunication(state, u))
        		continue;

			applyValue(state, result, u);
        }

        return result;
    }

    private boolean isInCommunication(GameState state, Unit unit){
		return state.isInCommunication(unit.getPlayer(), unit.getX(), unit.getY());
	}

	private void applyValue(GameState state, double[][] map, Unit unit){
		int value = unit.getUnitData().getAtkValue();
		int range = unit.getUnitData().getFightRange();

		int x = unit.getX();
		int y = unit.getY();


		/*			0 1 2
		 *			3 u 4
		 *			5 6 7
		 */
		boolean[] axis = {true, true, true, true, true, true, true, true};

		for (int i = 0; i <= range; ++i){
			axis[0] = apply(state, map, value,x - i, y - i, axis[0]);
			axis[1] = apply(state, map, value,x, y - i, axis[1]);
			axis[2] = apply(state, map, value,x + i, y - i, axis[2]);
			axis[3] = apply(state, map, value,x - i, y, axis[3]);
			axis[4] = apply(state, map, value,x + i, y, axis[4]);
			axis[5] = apply(state, map, value,x - i, y + i, axis[5]);
			axis[6] = apply(state, map, value, x, y + i, axis[6]);
			axis[7] = apply(state, map, value, x + i, y + i, axis[7]);
		}
	}

	private boolean apply(GameState state, double[][] map, int value, int x, int y, boolean b){
		if (!b)
			return false;

		if (state.isBuilding(x, y) && !state.getBuildingType(x, y).isAccessible())
			return false;

		map[x][y] += value;
		return true;
	}

}
