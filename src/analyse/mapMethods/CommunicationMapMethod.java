package analyse.mapMethods;

import game.EPlayer;
import game.gameState.GameState;
import javafx.scene.paint.Paint;

public class CommunicationMapMethod implements IMetricsMapMethod {

	//ignore the player argument btw
	@Override
	public double[][] compute(GameState state, EPlayer player) {
		double[][] result = new double[state.getWidth()][state.getHeight()];
		for (int i = 0; i < state.getWidth(); ++i)
			for (int j = 0; j < state.getHeight(); ++j)
			{
				if (state.isInCommunication(EPlayer.PLAYER_NORTH, i, j))
					result[i][j] += 1;
				if (state.isInCommunication(EPlayer.PLAYER_NORTH, i, j))
					result[i][j] += 2;
			}

		return result;
	}

	@Override
	public Paint getPaint(double mapValue) {
		return null;
	}
}
