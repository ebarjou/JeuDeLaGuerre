package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

import java.util.List;

public class CheckCanMoveUnit implements IRule {



    public String toString(){
        return this.getClass().getSimpleName();
    }

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
