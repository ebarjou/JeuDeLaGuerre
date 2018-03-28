package analyse.mapMethods;

import analyse.EMetricsMapType;
import analyse.EMetricsMoveType;
import analyse.InfoModule;
import analyse.MoveWrapper;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import javafx.scene.paint.Paint;
import ruleEngine.Coordinates;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitProperty;
import ruleEngine.exceptions.IncorrectGameActionException;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;

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
        for (double[] d : result)
            Arrays.fill(d, startValue);

        Collection<Unit> availableUnits = InfoModule.getAllUnitsFromPlayer(state, player);
        Collection<MoveWrapper> availableMoves = InfoModule.getAvailableMoves(EMetricsMoveType.ALL_AVAILABLE_MOVES, state, player);
        GameState fakeState = state.clone();
        fakeState.setActualPlayer(player);
        Collection<Unit> disabledUnits = state.getPriorityUnits();
        for (Unit unit : disabledUnits) {
            Optional<Unit> toBeRemoved = availableUnits.stream().filter(u -> u.getX() == unit.getX() && u.getY() == unit.getY()).findFirst();
            toBeRemoved.ifPresent(availableUnits::remove);
            fakeState.removePriorityUnit(new Coordinates(unit.getX(), unit.getY()));
        }

        sourceCoordinates = new LinkedList<>();
        for (Unit unit : availableUnits) {
            sourceCoordinates.add(new Coordinates(unit.getX(), unit.getY()));
        }

        for (MoveWrapper moveWrapper : availableMoves) {
            if (!fakeState.getUnitType(moveWrapper.getSourceCoordinates().getX(), moveWrapper.getSourceCoordinates().getY()).isCanAttack())
                continue;

            GameAction gameAction = new GameAction(player, EGameActionType.MOVE);
            GameState fakeState2 = fakeState.clone();
            gameAction.setSourceCoordinates(moveWrapper.getSourceCoordinates().getX(), moveWrapper.getSourceCoordinates().getY());
            gameAction.setTargetCoordinates(moveWrapper.getTargetCoordinates().getX(), moveWrapper.getTargetCoordinates().getY());
            int xo = moveWrapper.getTargetCoordinates().getX();
            int yo = moveWrapper.getTargetCoordinates().getY();
            try {
                RuleResult ruleResult = InfoModule.getRuleChecker().checkAndApplyAction(fakeState2, gameAction);
                if (!ruleResult.isValid() || !fakeState2.isInCommunication(player, xo, yo))
                    continue;

                EUnitProperty unitType = fakeState2.getUnitType(xo, yo);
                int range = unitType.getFightRange();
                int x1 = Math.max(xo - range, 0);
                int x2 = Math.min(xo + range, state.getWidth() - 1);
                int y1 = Math.max(yo - range, 0);
                int y2 = Math.min(yo + range, state.getHeight() - 1);
                gameAction.setActionType(EGameActionType.ATTACK);
                gameAction.setSourceCoordinates(xo, yo);
                for (int i = x1; i <= x2; ++i)
                    for (int j = y1; j <= y2; ++j) {
                        GameState fakeState3 = fakeState2.clone();
                        gameAction.setTargetCoordinates(i, j);
                        Unit target = new Unit(EUnitProperty.RELAY, player.next());
                        target.setPosition(i, j);
                        fakeState3.addUnit(target);
                        if (InfoModule.getRuleChecker().checkAction(fakeState3, gameAction))
                            result[i][j] += unitType.getAtkValue();
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
