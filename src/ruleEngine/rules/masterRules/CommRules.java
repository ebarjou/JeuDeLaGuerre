package ruleEngine.rules.masterRules;

import game.EPlayer;
import game.board.Board;
import game.board.Building;
import game.board.EDirection;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import java.util.List;

public class CommRules extends MasterRule {

    public CommRules(){
    }

    @Override
    public void applyResult(Board board, GameState state, GameAction action, RuleResult result) {
        computeCommunication(board, state);
    }

    private void computeCommunication(Board board, GameState gameState){
        board.clearCommunication();
        board.clearMarked();
        List<Building> allBuildings = gameState.getAllBuildings();

        for(Building building : allBuildings) {
            if (building.getBuildingData() == EBuildingData.ARSENAL && !building.isBroken()) {
                int x = building.getX();
                int y = building.getY();
                board.setInCommunication(board.getBuildingPlayer(x, y), x, y, true);
                board.setMarked(x, y, true);
                board.cellToString(x, y);
                for (EDirection direction : EDirection.values())
                    createCom(board, x, y, direction, board.getBuildingPlayer(x, y), -1);
            }
        }
    }

    private void createCom(Board board, int x, int y, EDirection dir, EPlayer player, int rangeMax){
        x += dir.getX();
        y += dir.getY();
        int dist = 1;
        while(!isObstacle(board, x, y, player) && (rangeMax < 0 || dist <= rangeMax)){
            board.setInCommunication(player, x, y, true);
            EUnitData u;
            try {
                u = board.getUnitType(x, y);
            } catch(NullPointerException e) {
                u = null;
            }
            if(u != null && board.getUnitPlayer(x, y) == player && !board.isMarked(x, y)){
                board.setMarked(x, y, true);
                int rangeUnit = 1;
                if(u.isRelayCommunication())
                    rangeUnit = -1;

                for(EDirection d : EDirection.values())
                    createCom(board, x, y, d, player, rangeUnit);
            }
            x += dir.getX();
            y += dir.getY();
            dist++;
        }
    }

    private boolean isObstacle(Board board, int x, int y, EPlayer player){
        if(!board.isValidCoordinate(x, y))
            return true;
        EUnitData u;
        EBuildingData b;
        try {
            u = board.getUnitType(x, y);
        } catch(NullPointerException e) {
            u = null;
        }
        try {
            b = board.getBuildingType(x, y);
        } catch(NullPointerException e) {
            b = null;
        }
        return (b != null && b == EBuildingData.MOUNTAIN)
                || (u != null && board.getUnitPlayer(x, y) != player && !u.isRelayCommunication());
    }

}
