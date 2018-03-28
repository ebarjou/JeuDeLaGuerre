package analyse.mapMethods;

import analyse.EMetricsMapType;
import game.EPlayer;
import game.gameState.GameState;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ImpreciseOutcomePredictionMapMethod implements IMetricsMapMethod {
    @Override
    public double[][] compute(GameState state, EPlayer player) {
        double[][] atkMap = EMetricsMapType.ATTACK_MAP_FAST.getMethod().compute(state, player.next());
        double[][] defMap = EMetricsMapType.DEFENSE_MAP_FAST.getMethod().compute(state, player);
        double[][] result = new double[state.getWidth()][state.getHeight()];

        for (int i = 0; i < state.getWidth(); ++i)
            for (int j = 0; j < state.getHeight(); ++j)
                result[i][j] = defMap[i][j] - atkMap[i][j];

        return result;
    }

    @Override
    public Paint getPaint(double value) {
        if ((int) value == 0)
            return Color.rgb(0, 0, 0, 0);

        Color weak = Color.RED;
        Color strong = Color.GREEN;
        Color result;
        if (value < 0)
            result = weak;
        else
            result = strong;

        double alpha = Math.abs(value / 80) + 0.05;
        return result.deriveColor(1, 1, 1, Math.min(1, alpha));
    }
}
