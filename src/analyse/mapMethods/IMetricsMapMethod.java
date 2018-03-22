package analyse.mapMethods;

import game.EPlayer;
import game.gameState.GameState;
import ui.UIElements.IDrawableInfo;

public interface IMetricsMapMethod extends IDrawableInfo{
    double[][] compute(GameState state, EPlayer player);
}
