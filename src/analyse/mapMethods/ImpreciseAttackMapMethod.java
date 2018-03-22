package analyse.mapMethods;

import analyse.EMetricsMapType;
import analyse.InfoModule;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.Collection;

public class ImpreciseAttackMapMethod implements IMetricsMapMethod {
	@Override
	public double[][] compute(GameState state, EPlayer player) {
		double[][] result = new double[state.getWidth()][state.getHeight()];
		double startValue = (player.equals(EPlayer.PLAYER_SOUTH) ? 0.2 : 0.1);
		for(double[] d : result)
			Arrays.fill(d, startValue);

		Collection<Unit> units = InfoModule.getAllUnitsFromPlayer(state, player);
		for (Unit u : units){
			if (!u.getUnitData().isCanAttack())
				continue;

			int range = u.getUnitData().getFightRange() + u.getUnitData().getMovementValue();
			int x1 = Math.max(u.getX() - range, 0);
			int y1 = Math.max(u.getY() - range, 0);
			int x2 = Math.min(u.getX() + range, state.getWidth() - 1);
			int y2 = Math.min(u.getY() + range, state.getHeight() - 1);
			for (int i = x1; i <= x2; ++i)
				for (int j = y1; j <= y2; ++j)
					result[i][j] += u.getUnitData().getAtkValue();

		}

		return result;
	}

	@Override
	public Paint getPaint(double mapValue) {
		return EMetricsMapType.STATIC_ATTACK_MAP.getDrawMethod().getPaint(mapValue);
	}
}
