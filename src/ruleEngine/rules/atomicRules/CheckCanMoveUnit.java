package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.List;

/**
 * Check if a unit can move or not. A unit may not be able to move if it has moved or attacked previously on the same turn.<br>
 * Valid if the unit can move, invalid otherwise.
 *
 * @see ruleEngine.rules.masterRules.MoveRules
 */
public class CheckCanMoveUnit extends Rule {
    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        boolean canMove = isUnitCanMove(state, src);
        if(!canMove){
            result.invalidate();
            result.addMessage(this, "This unit has already moved.");
            return false;
        }

        return true;
    }

    private boolean isUnitCanMove(GameState state, Coordinates coords){
        List<Unit> allUnits = state.getAllUnits();
        for(Unit unit : allUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                if(unit.getCanMove())
                    return true;
        return false;
    }
}
