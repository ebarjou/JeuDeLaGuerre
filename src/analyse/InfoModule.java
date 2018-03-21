package analyse;

import analyse.mapMethods.IMetricsMapMethod;
import analyse.moveMethods.IMetricsMoveMethod;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.RuleChecker;

import java.util.Collection;
import java.util.stream.Collectors;

public class InfoModule {

	private static RuleChecker ruleChecker;

	public static double[][] getInfoMap(EMetricsMapType type, GameState gameState, EPlayer player){
		IMetricsMapMethod m = type.getMethod();
		return m.compute(gameState, player);
	}

	public static Collection<MoveWrapper> getAvailableMoves(EMetricsMoveType type, GameState gameState, EPlayer player){
		IMetricsMoveMethod m = type.getMethod();
		return m.compute(gameState, player);
	}

	public static Collection<Unit> getAllUnitsFromPlayer(GameState state, EPlayer player){
		return state.getAllUnits().stream().filter((unit -> unit.getPlayer().equals(player))).collect(Collectors.toList());
	}

	public static RuleChecker getRuleChecker(){
		if (ruleChecker == null)
			ruleChecker = new RuleChecker();

		return ruleChecker;
	}
}
