package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.List;

public class CheckCanAttackUnit extends Rule {

    private boolean isUnitCanAttack(IGameState state, Coordinates coords){
        List<Unit> cantAttackUnits = state.getCantAttackUnits();
        for(Unit unit : cantAttackUnits)
            if(unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return false;

        return true;
    }

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        boolean canAttack = isUnitCanAttack(state, src);
        if(!canAttack){
            result.invalidate();
            result.addMessage(this, "This unit can't attack this turn.");
            return false;
        }

        return true;
    }
}
