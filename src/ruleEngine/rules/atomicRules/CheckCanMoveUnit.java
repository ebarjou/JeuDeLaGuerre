package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

import java.util.List;

public class CheckCanMoveUnit implements IRule{
    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        boolean canMove = isUnitCanMove(state, src);
        if(!canMove){
            result.invalidate();
            result.addMessage(this, "This unit has already moved.");
            return false;
        }

        return true;
    }

    private boolean isUnitCanMove(IGameState state, Coordinates coords){
        List<Unit> allUnits = state.getAllUnits();
        for(Unit unit : allUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                if(unit.getCanMove())
                    return true;
        return false;
    }
}
