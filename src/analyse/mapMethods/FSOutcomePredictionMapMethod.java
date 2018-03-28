package analyse.mapMethods;

import analyse.EMetricsMapType;
import game.EPlayer;
import game.gameState.GameState;
import javafx.scene.paint.Paint;

public class FSOutcomePredictionMapMethod implements IMetricsMapMethod {
	@Override
	public double[][] compute(GameState state, EPlayer player) {
		double[][] atkMap = EMetricsMapType.ATTACK_MAP_FAST.getMethod().compute(state, player.next());
		double[][] defMap = EMetricsMapType.DEFENSE_MAP_STATIC.getMethod().compute(state, player);
		double[][] result = new double[state.getWidth()][state.getHeight()];

		for (int i = 0; i < state.getWidth(); ++i)
			for (int j = 0; j < state.getHeight(); ++j)
				result[i][j] = defMap[i][j] - atkMap[i][j];

		return result;
	}

	@Override
	public Paint getPaint(double value) {
		return EMetricsMapType.OUTCOME_MAP_FAST.getPaint(value);
	}
}
