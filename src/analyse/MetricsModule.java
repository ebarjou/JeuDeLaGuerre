package analyse;

import analyse.metricsMethods.IMetricsMapMethod;
import game.EPlayer;
import game.gameState.GameState;

public class MetricsModule {

	public static double[][] getInfoMap(EMetricsMapType type, GameState gameState, EPlayer player){
		IMetricsMapMethod m = type.getMethod();
		return m.compute(gameState, player);

	}
}
