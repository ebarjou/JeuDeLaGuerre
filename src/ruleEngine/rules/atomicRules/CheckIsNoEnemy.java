package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.List;

/**
 * Check if there is no more enemy units on the board, leading to the end of the game.<br>
 * Valid if there is no more enemy units, invalid otherwise.<br><br>
 *
 * NOTE : Because of the incomplete implementation of rule dependencies, this rule is checked in {@link CheckIsNoArsenalEnemy} instead.
 *
 * @see ruleEngine.rules.masterRules.VictoryRules
 * @see CheckIsNoArsenalEnemy
 */
@Deprecated
public class CheckIsNoEnemy extends Rule {
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
}
