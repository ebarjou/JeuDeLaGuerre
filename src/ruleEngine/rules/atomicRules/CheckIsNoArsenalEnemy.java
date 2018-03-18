package ruleEngine.rules.atomicRules;

import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;

import java.util.List;

/**
 * Check if all of the adversary's arsenals are destroyed or not, leading to the end of a game.<br>
 * Valid if there's no more enemy arsenals, invalid otherwise.<br><br>
 * NOTE : Because of the incomplete implementation of the rule dependencies, all of the victory conditions are checked in
 * this one, meaning this rule also checks if there is no more enemy units on the board, and the rule is valid if one of these
 * condition is verified, and invalid if no one is.
 *
 * @see ruleEngine.rules.masterRules.VictoryRules
 * @see CheckIsNoEnemy
 */
public class CheckIsNoArsenalEnemy extends Rule {
    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        boolean isNoArsenalEnemy = true;
        List<Building> buildings = state.getAllBuildings();

        for(Building building : buildings) {
            if (building.getBuildingData() == EBuildingData.ARSENAL
                    && building.getPlayer() != action.getPlayer()) {
                isNoArsenalEnemy = false;
            }
        }
        boolean isNoEnemy = true;
        List<Unit> units = state.getAllUnits();

        for(Unit u : units){
            if(u.getPlayer() != action.getPlayer() && u.getUnitData().isCanAttack())
                isNoEnemy = false;
        }

        boolean victory = isNoArsenalEnemy || isNoEnemy;
        if(!victory)
            result.invalidate();
        else
            result.addMessage(this, action.getPlayer() + " winner !");
        return victory;
    }
}
