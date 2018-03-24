package analyse.mapMethods;

import analyse.EMetricsMapType;
import game.EPlayer;
import game.gameState.GameState;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;

public class ImpreciseOutcomePredictionMapMethod implements IMetricsMapMethod {
    @Override
    public double[][] compute(GameState state, EPlayer player) {
        double[][] atkMap = EMetricsMapType.ATTACK_MAP_FAST.getMethod().compute(state, player.other());
        double[][] defMap = EMetricsMapType.DEFENSE_MAP_FAST.getMethod().compute(state, player);
        double[][] result = new double[state.getWidth()][state.getHeight()];

        for (int i = 0; i < state.getWidth(); ++i )
            for (int j = 0; j < state.getHeight(); ++j)
                result[i][j] = defMap[i][j] - atkMap[i][j];

        return result;
    }

    @Override
    public Paint getPaint(double value) {
        if ((int) value == 0)
            return Color.rgb(0, 0, 0, 0);

        Color weak = Color.DARKRED;
        Color strong = Color.DARKGREEN;
        Color result;
        if (value < 0)
            result = weak;
        else
            result = strong;

        return result.deriveColor(1, 1, 1, Math.min(1, Math.abs(value/30) + 0.05));
    }
}
