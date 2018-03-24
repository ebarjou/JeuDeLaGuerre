package analyse.mapMethods;

import analyse.EMetricsMapType;
import analyse.EMetricsMoveType;
import analyse.InfoModule;
import analyse.MoveWrapper;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import ruleEngine.Coordinates;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;

import java.util.*;

public class DeepAttackMapMethod implements IMetricsMapMethod {

	private Collection<Coordinates> sourceCoordinates;

	private int deep;    //Number of moves to take in account

	public DeepAttackMapMethod(int deep) {
		this.deep = deep;
	}

	@Override
	public double[][] compute(GameState state, EPlayer player) {
		double[][] result = new double[state.getWidth()][state.getHeight()];
		double startValue = (player.equals(EPlayer.PLAYER_SOUTH) ? 0.2 : 0.1);
		for(double[] d : result)
			Arrays.fill(d, startValue);

//		Collection<Unit> units = InfoModule.getAllUnitsFromPlayer(state, player);
//		for (Unit u : units){
//			int range = u.getUnitData().getFightRange() + u.getUnitData().getMovementValue();
//			int x1 = Math.max(u.getX() - range, 0);
//			int x2 = Math.min(u.getX() + range, state.getWidth() - 1);
//			int y1 = Math.max(u.getY() - range, 0);
//			int y2 = Math.min(u.getY() + range, state.getHeight() - 1);
//
//			for (int i = x1; i <= x2; ++i)
//				for(int j = y1; j <= y2; ++j){
//
//				}
//		}

		Collection<Unit> availableUnits = InfoModule.getAllUnitsFromPlayer(state, player);
		Collection<MoveWrapper> availableMoves = InfoModule.getAvailableMoves(EMetricsMoveType.ALL_AVAILABLE_MOVES, state, player);
		GameState fake = state.clone();
		fake.setActualPlayer(player);
		Collection<Unit> disabledUnits = state.getPriorityUnits();
		for (Unit u : disabledUnits) {
			Optional<Unit> toBeRemoved = availableUnits.stream().filter(unit -> unit.getX() == u.getX() && unit.getY() == u.getY()).findFirst();
			toBeRemoved.ifPresent(availableUnits::remove);
			fake.removePriorityUnit(new Coordinates(u.getX(), u.getY()));
		}

		sourceCoordinates = new LinkedList<>();
		for (Unit u : availableUnits) {
			sourceCoordinates.add(new Coordinates(u.getX(), u.getY()));
		}

		for (MoveWrapper mw : availableMoves) {
			if (!fake.getUnitType(mw.getSourceCoordinates().getX(), mw.getSourceCoordinates().getY()).isCanAttack())
				continue;

			GameAction gameAction = new GameAction(player, EGameActionType.MOVE);
			GameState fake2 = fake.clone();
			gameAction.setSourceCoordinates(mw.getSourceCoordinates().getX(), mw.getSourceCoordinates().getY());
			gameAction.setTargetCoordinates(mw.getTargetCoordinates().getX(), mw.getTargetCoordinates().getY());
			int xo = mw.getTargetCoordinates().getX();
			int yo = mw.getTargetCoordinates().getY();
			try {
				RuleResult r = InfoModule.getRuleChecker().checkAndApplyAction(fake2, gameAction);
				if (!r.isValid() || !fake2.isInCommunication(player, xo, yo))
					continue;

				EUnitData u = fake2.getUnitType(xo, yo);
				int range = u.getFightRange();
				int x1 = Math.max(xo - range, 0);
				int x2 = Math.min(xo + range, state.getWidth() - 1);
				int y1 = Math.max(yo - range, 0);
				int y2 = Math.min(yo + range, state.getHeight() - 1);
				gameAction.setActionType(EGameActionType.ATTACK);
				gameAction.setSourceCoordinates(xo, yo);
				for (int i = x1; i <= x2; ++i)
					for (int j = y1; j <= y2; ++j) {
						GameState fake3 = fake2.clone();
						gameAction.setTargetCoordinates(i, j);
						Unit target = new Unit(EUnitData.RELAY, player.other());
						target.setPosition(i, j);
						fake3.addUnit(target);
						if (InfoModule.getRuleChecker().checkAction(fake3, gameAction))
							result[i][j] += u.getAtkValue();

					}
			} catch (IncorrectGameActionException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public Paint getPaint(double mapValue) {
		return EMetricsMapType.STATIC_ATTACK_MAP.getPaint(mapValue);
	}
}
