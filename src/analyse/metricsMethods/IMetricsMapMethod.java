package analyse.metricsMethods;

import game.EPlayer;
import game.gameState.GameState;

public interface IMetricsMapMethod {
    double[][] compute(GameState state, EPlayer player);
}
