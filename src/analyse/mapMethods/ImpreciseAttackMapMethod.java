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

public class ImpreciseAttackMapMethod extends AbstractImpreciseMapMethod{

	public ImpreciseAttackMapMethod() {
		super(true);
	}

	@Override
	public Paint getPaint(double mapValue) {
		return EMetricsMapType.STATIC_ATTACK_MAP.getPaint(mapValue);
	}
}
