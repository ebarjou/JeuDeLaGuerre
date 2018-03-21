package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

import java.util.List;

public class CheckCanAttackUnit implements IRule {

    private boolean isUnitCanAttack(GameState state, Coordinates coords){
        List<Unit> cantAttackUnits = state.getCantAttackUnits();
        for(Unit unit : cantAttackUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return false;

        return true;
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        boolean canAttack = isUnitCanAttack(state, src);
        if(!canAttack){
            result.invalidate();
            result.addMessage(this, "This unit can't attack this turn.");
            return false;
        }

        return true;
    }


    public String toString(){
        return this.getClass().getSimpleName();
    }
}
