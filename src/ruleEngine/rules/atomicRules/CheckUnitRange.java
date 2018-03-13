package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.List;

public class CheckUnitRange extends Rule {

    private boolean isAlignedCharge(IGameState state, GameAction action) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();

        int dirX = dst.getX() - src.getX();
        int dirY = dst.getY() - src.getY();
        int diffX = Math.abs(dirX);
        int diffY = Math.abs(dirY);
        if (( diffX != diffY ) && (dirX != 0) && (dirY != 0))   return false;

        if (diffX != 0) dirX = dirX / diffX; // 1 or -1
        if (diffY != 0) dirY = dirY / diffY; // 1 or -1

        int x = src.getX();
        int y = src.getY();
        while (x != dst.getX() || y != dst.getY()) {
            if ( !state.isUnit(x, y)
                    || !state.getUnitType(x, y).isCanCharge()
                    || !(state.getUnitPlayer(x, y) == action.getPlayer())
                    || !isUnitCanAttack(state, new Coordinates(x, y)) ) {
                return false;
            }
            x += dirX;
            y += dirY;
        }
        return true;
    }

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        try {
            int x = action.getSourceCoordinates().getX();
            int y = action.getSourceCoordinates().getY();
            int x2 = action.getTargetCoordinates().getX();
            int y2 = action.getTargetCoordinates().getY();

            int range = state.getUnitType(x, y).getFightRange();
            int dist = state.getDistance(x, y, x2, y2);

            if (dist > range && !isAlignedCharge(state, action)) {
                result.addMessage(this,
                        "Not enough range to attack, the unit has a range of "
                                + range + ", and you need a range of " + dist + ".");
                result.invalidate();
                return false;
            }
            return true;
        } catch (NullPointerException e){
            result.addMessage(this, "Not enough range to attack.");
            result.invalidate();
            return false;
        }
    }


    private boolean isUnitCanAttack(IGameState state, Coordinates coords){
        List<Unit> cantAttackUnits = state.getCantAttackUnits();
        for(Unit unit : cantAttackUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return false;
        return true;
    }
}
