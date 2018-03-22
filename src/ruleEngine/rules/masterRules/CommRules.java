package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.board.Building;
import game.board.EDirection;
import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.newRules.RuleCompositeAND;

import java.util.List;

/**
 * Class not testing any rule but compute the communications on the map according to the terrain and unit types.
 * Called by the RuleChecker by the {@code computeCommunications()} private method on the demand of the Game object.
 */
public class CommRules extends RuleCompositeAND {

    public CommRules(){
    }

    public void applyResult(GameState state, GameAction action, RuleResult result) {
        computeCommunication(state);
    }

    private void computeCommunication(GameState gameState){
        gameState.clearCommunication();
        gameState.clearMarked();
        List<Building> allBuildings = gameState.getAllBuildings();

        for(Building building : allBuildings) {
            if (building.getBuildingData() == EBuildingData.ARSENAL && !building.isBroken()) {
                int x = building.getX();
                int y = building.getY();
                gameState.setInCommunication(gameState.getBuildingPlayer(x, y), x, y, true);
                gameState.setMarked(x, y, true);
                for (EDirection direction : EDirection.values())
                    createCom(gameState, x, y, direction, gameState.getBuildingPlayer(x, y), -1);
            }
        }
    }

    private void createCom(GameState gameState, int x, int y, EDirection dir, EPlayer player, int rangeMax){
        x += dir.getX();
        y += dir.getY();
        int dist = 1;
        while(!isObstacle(gameState, x, y, player) && (rangeMax < 0 || dist <= rangeMax)){
            gameState.setInCommunication(player, x, y, true);
            EUnitData u;
            try {
                u = gameState.getUnitType(x, y);
            } catch(IllegalBoardCallException e) {
                u = null;
            }
            if(u != null && gameState.getUnitPlayer(x, y) == player && !gameState.isMarked(x, y)){
                gameState.setMarked(x, y, true);
                int rangeUnit = 1;
                if(u.isRelayCommunication())
                    rangeUnit = -1;

                for(EDirection d : EDirection.values())
                    createCom(gameState, x, y, d, player, rangeUnit);
            }
            x += dir.getX();
            y += dir.getY();
            dist++;
        }
    }

    private boolean isObstacle(GameState gameState, int x, int y, EPlayer player){
        if(!gameState.isValidCoordinate(x, y))
            return true;
        EUnitData u;
        EBuildingData b;
        try {
            u = gameState.getUnitType(x, y);
        } catch(IllegalBoardCallException e) {
            u = null;
        }
        try {
            b = gameState.getBuildingType(x, y);
        } catch(IllegalBoardCallException e) {
            b = null;
        }
        return (b != null && b == EBuildingData.MOUNTAIN)
                || (u != null && gameState.getUnitPlayer(x, y) != player && !u.isRelayCommunication());
    }

}
