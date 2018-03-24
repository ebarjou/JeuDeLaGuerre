package analyse.mapMethods;

import analyse.InfoModule;
import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import javafx.scene.paint.Paint;

import java.util.Arrays;
import java.util.Collection;

public abstract class AbstractImpreciseMapMethod implements IMetricsMapMethod {

    private boolean usingAttack;    //False : using Def

    public AbstractImpreciseMapMethod(boolean usingAttack){
        this.usingAttack = usingAttack;
    }

    @Override
    public double[][] compute(GameState state, EPlayer player) {
        double[][] result = new double[state.getWidth()][state.getHeight()];
        double startValue = (player.equals(EPlayer.PLAYER_SOUTH) ? 0.2 : 0.1);
        for(double[] d : result)
            Arrays.fill(d, startValue);

        Collection<Unit> units = InfoModule.getAllUnitsFromPlayer(state, player);
        for (Unit u : units){
            if (usingAttack && !u.getUnitData().isCanAttack())
                continue;

            int range = u.getUnitData().getFightRange() + u.getUnitData().getMovementValue();
            int x1 = Math.max(u.getX() - range, 0);
            int y1 = Math.max(u.getY() - range, 0);
            int x2 = Math.min(u.getX() + range, state.getWidth() - 1);
            int y2 = Math.min(u.getY() + range, state.getHeight() - 1);
            double value = (usingAttack ? u.getUnitData().getAtkValue() : u.getUnitData().getDefValue());
            for (int i = x1; i <= x2; ++i)
                for (int j = y1; j <= y2; ++j)
                    result[i][j] += value + (usingAttack && state.isBuilding(i, j) ? state.getBuildingType(i, j).getBonusDef() : 0);

        }

        return result;
    }

}
