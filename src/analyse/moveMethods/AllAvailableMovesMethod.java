package analyse.moveMethods;

import analyse.InfoModule;
import analyse.MoveWrapper;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.exceptions.IncorrectGameActionException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllAvailableMovesMethod implements IMetricsMoveMethod {
	@Override
	public Collection<MoveWrapper> compute(GameState gameState, EPlayer player) {
		Collection<Unit> units = InfoModule.getAllUnitsFromPlayer(gameState, player);
		GameState fake = gameState.clone();
		fake.setActualPlayer(player);
		List<MoveWrapper> actionList = new ArrayList<>();
		for (Unit unit : units) {
			for (int i = -unit.getUnitData().getMovementValue(); i <= unit.getUnitData().getMovementValue(); ++i) {
				for (int j = -unit.getUnitData().getMovementValue(); j <= unit.getUnitData().getMovementValue(); ++j) {
					MoveWrapper m = new MoveWrapper(new Coordinates(unit.getX(), unit.getY()), new Coordinates(unit.getX() + i, unit.getY() + j));
					try {
						GameAction ga = new GameAction(player, EGameActionType.MOVE);
						ga.setSourceCoordinates(m.sourceCoordinates.getX(), m.sourceCoordinates.getY());
						ga.setTargetCoordinates(m.targetCoordinates.getX(), m.targetCoordinates.getY());
						if (InfoModule.getRuleChecker().checkAction(fake, ga)) {
							actionList.add(m);
						}
					} catch (IncorrectGameActionException ignore) {
					}
				}
			}
		}

		return actionList;
	}
}
