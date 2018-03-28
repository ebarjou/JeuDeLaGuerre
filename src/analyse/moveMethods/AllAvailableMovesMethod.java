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
        return generateActionList(units, fake, player);

    }

    private Collection<MoveWrapper> generateActionList(Collection<Unit> units, GameState state, EPlayer player) {
        state.setActualPlayer(player);
        List<MoveWrapper> actionList = new ArrayList<>();
        for (Unit unit : units) {
            for (int i = -unit.getUnitData().getMovementValue(); i <= unit.getUnitData().getMovementValue(); ++i) {
                for (int j = -unit.getUnitData().getMovementValue(); j <= unit.getUnitData().getMovementValue(); ++j) {
                    MoveWrapper moveWrapper = new MoveWrapper(new Coordinates(unit.getX(), unit.getY()), new Coordinates(unit.getX() + i, unit.getY() + j));
                    try {
                        GameAction gameAction = new GameAction(player, EGameActionType.MOVE);
                        gameAction.setSourceCoordinates(moveWrapper.sourceCoordinates.getX(), moveWrapper.sourceCoordinates.getY());
                        gameAction.setTargetCoordinates(moveWrapper.targetCoordinates.getX(), moveWrapper.targetCoordinates.getY());
                        if (InfoModule.getRuleChecker().checkAction(state, gameAction)) {
                            actionList.add(moveWrapper);
                        }
                    } catch (IncorrectGameActionException ignore) {
                    }
                }
            }
        }

        return actionList;
    }
}
