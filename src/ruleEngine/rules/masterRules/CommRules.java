package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.board.Building;
import game.board.EDirection;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;
import ruleEngine.rules.newRules.RuleCompositeAND;

import java.util.List;

/**
 * Class not testing any rule but compute the communications on the map according to the terrain and unit types.
 * Called by the RuleChecker by the {@code computeCommunications()} private method on the demand of the Game object.
 */
public class CommRules extends RuleCompositeAND {

    public void applyResult(GameState state, GameAction action, RuleResult result) {
        computeCommunication(state);
    }

    private void computeCommunication(GameState gameState) {
        gameState.clearCommunication();
        gameState.clearMarked();
        List<Building> allBuildings = gameState.getAllBuildings();

        for (Building building : allBuildings) {
            if (building.getBuildingData() == EBuildingProperty.ARSENAL) {
                int x = building.getX();
                int y = building.getY();
                gameState.setInCommunication(gameState.getBuildingPlayer(x, y), x, y, true);
                gameState.setMarked(x, y, true);
                for (EDirection direction : EDirection.values())
                    createCom(gameState, x, y, direction, gameState.getBuildingPlayer(x, y), -1);
            }
        }
    }

    private void createCom(GameState gameState, int x, int y, EDirection dir, EPlayer player, int rangeMax) {
        x += dir.getX();
        y += dir.getY();
        int dist = 1;
        while (!isObstacle(gameState, x, y, player) && (rangeMax < 0 || dist <= rangeMax)) {
            gameState.setInCommunication(player, x, y, true);
            EUnitProperty unitProperty = (gameState.isUnit(x, y) ? gameState.getUnitType(x, y) : null);

            if(unitProperty != null && gameState.getUnitPlayer(x, y) == player && !gameState.isMarked(x, y)){
                gameState.setMarked(x, y, true);
                int rangeUnit = 1;
                if (unitProperty.isRelayCommunication())
                    rangeUnit = -1;

                for (EDirection d : EDirection.values())
                    createCom(gameState, x, y, d, player, rangeUnit);
            }

            x += dir.getX();
            y += dir.getY();
            dist++;
        }
    }

    private boolean isObstacle(GameState gameState, int x, int y, EPlayer player) {
        if (!gameState.isValidCoordinate(x, y))
            return true;
        EUnitProperty unitProperty = (gameState.isUnit(x, y) ? gameState.getUnitType(x, y) : null);
        EBuildingProperty buildingProperty = (gameState.isBuilding(x, y) ? gameState.getBuildingType(x, y) : null);
        return (buildingProperty != null && buildingProperty == EBuildingProperty.MOUNTAIN)
                || (unitProperty != null && gameState.getUnitPlayer(x, y) != player && !unitProperty.isRelayCommunication());
    }

}
