package analyse.metricsMethods;

import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;

import java.util.List;
import java.util.stream.Collectors;

public class AttackMapMethod implements IMetricsMapMethod {

    @Override
    public double[][] compute(GameState state, EPlayer player) {
    	//THIS METHOD IS INCORRECT, RANGE OBSTRUCTIONS AND COMMUNICATIONS ARE NOT CHECKED
        double[][] result = new double[state.getWidth()][state.getHeight()];
        List<Unit> units = state.getAllUnits().stream().filter((unit -> unit.getPlayer().equals(player))).collect(Collectors.toList());
        for(Unit u : units){
            int value = u.getUnitData().getAtkValue();
            int range = u.getUnitData().getFightRange();

            int x = u.getX();
            int y = u.getY();

            for (int i = -range; i <= range; ++i){
                result[x + i][y] += value;
                result[x][y + i] += value;
                result[x + i][y + i] += value;
                result[x + i][y - i] += value;
            }
        }

        return result;
    }
}
