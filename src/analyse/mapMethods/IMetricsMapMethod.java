package analyse.mapMethods;

import game.EPlayer;
import game.gameState.GameState;
import javafx.scene.paint.Paint;

public interface IMetricsMapMethod {
    double[][] compute(GameState state, EPlayer player);

    Paint getPaint(double value);
}
