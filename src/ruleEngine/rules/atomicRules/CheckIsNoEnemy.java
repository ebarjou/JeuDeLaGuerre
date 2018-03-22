package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

import java.util.List;

/**
 - * Check if there is no more enemy units on the board, leading to the end of the game.<br>
 - * Valid if there is no more enemy units, invalid otherwise.<br><br>
 - * @see ruleEngine.rules.masterRules.VictoryRules
 - * @see CheckIsNoArsenalEnemy
 - */
public class CheckIsNoEnemy implements IRule {
    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
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



    public String toString(){
        return this.getClass().getSimpleName();
    }
}
