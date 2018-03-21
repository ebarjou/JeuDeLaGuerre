package analyse.moveMethods;

import analyse.MoveWrapper;
import game.EPlayer;
import game.gameState.GameState;

import java.util.Collection;

public interface IMetricsMoveMethod {
	Collection<MoveWrapper> compute(GameState gameState, EPlayer player);
}
