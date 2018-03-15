package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.List;

public class CheckIsNoEnemy extends Rule {
    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        List<Unit> units = state.getAllUnits();
        boolean isNoEnemy = true;

        for(Unit u : units){
            if(u.getPlayer() != action.getPlayer() && u.getUnitData().isCanAttack()){
                isNoEnemy = false;
                result.invalidate();
            }
        }

        return isNoEnemy;
    }
}
