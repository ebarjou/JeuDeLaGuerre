package ruleEngine.rules.atomicRules;

import game.board.Building;
import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;

import java.util.List;

public class CheckIsNoArsenalEnemy extends Rule {
    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
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
