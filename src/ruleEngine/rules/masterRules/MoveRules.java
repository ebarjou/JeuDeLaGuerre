package ruleEngine.rules.masterRules;

import game.board.Building;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;
import ruleEngine.rules.atomicRules.*;
import ruleEngine.rules.newRules.IRule;
import ruleEngine.rules.newRules.RuleCompositeAnd;
import ruleEngine.rules.newRules.RuleCompositeAndDep;

import java.util.List;

public class MoveRules extends RuleCompositeAnd {

    public MoveRules() {
        super();
        //TODO: Put here the sub-rules (atomic) you need to check.
        super.add(new CheckPlayerTurn());
        super.add(new CheckPlayerMovesLeft());
        super.add(new CheckOnBoard());
        super.add(new CheckAbilityToMove());//comm

        IRule andDep = new RuleCompositeAndDep();
        andDep.add(new CheckIsAllyUnit());
        andDep.add(new CheckUnitMP());
        super.add(andDep);
        //super.add(new CheckUnitMP());
        //super.add(new CheckIsAllyUnit());
        ////addDependence(new CheckUnitMP(), new CheckIsAllyUnit(), true);

        super.add(new CheckIsPriorityUnit());
        super.add(new CheckCanMoveUnit());
        super.add(new CheckIsEmptyPath());
        /*
        addRule(new CheckPlayerTurn());
        addRule(new CheckPlayerMovesLeft());
        addRule(new CheckOnBoard());
        addRule(new CheckAbilityToMove());//comm
        addRule(new CheckIsAllyUnit());
        addRule(new CheckIsPriorityUnit());
        //addDependence(new CheckNoPriorityUnitAlly(), new CheckIsPriorityUnit(), );
        addRule(new CheckCanMoveUnit());
        addRule(new CheckUnitMP());
        addDependence(new CheckUnitMP(), new CheckIsAllyUnit(), true);
        addRule(new CheckIsEmptyPath());
        */
    }


    public void applyResult(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        state.removePriorityUnit(src);

        state.setUnitHasMoved(src);
        //state.updateUnitPosition(src, target);
        state.removeOneAction();
        state.moveUnit(src.getX(), src.getY(), target.getX(), target.getY());

        List<Building> buildings = state.getAllBuildings();
        Building remove = null;
        for(Building building : buildings){
            if(building.getPlayer() != action.getPlayer()
                    && building.getBuildingData() == EBuildingData.ARSENAL
                    && building.getX() == target.getX()
                    && building.getY() == target.getY()){
                remove = building;
                break;
            }
        }
        if(remove != null)
            state.removeBuilding(remove);
    }
}
