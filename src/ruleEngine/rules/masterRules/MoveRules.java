package ruleEngine.rules.masterRules;

import game.board.Building;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.rules.atomicRules.*;
import ruleEngine.rules.newRules.*;

import java.util.List;

public class MoveRules extends RuleCompositeAnd {

    public MoveRules() {
        super();
        //TODO: Put here the sub-rules (atomic) you need to check.
        super.add(new CheckPlayerTurn());
        super.add(new CheckPlayerMovesLeft());

        IRule dependentOnBoard = new RuleCompositeAndDep();
        dependentOnBoard.add(new CheckOnBoard());

        IRule rulesDependentOfOnBoard = new RuleCompositeAnd();

        IRule dependentIsUnit = new RuleCompositeAndDep();
        dependentIsUnit.add(new CheckIsAllyUnit());

        IRule rulesDependentOfIsUnit = new RuleCompositeAnd();

        IRule orRuleCommunication = new RuleCompositeOrDep();
        orRuleCommunication.add(new CheckIsInCommunication());
        orRuleCommunication.add(new CheckIsRelay());
        rulesDependentOfIsUnit.add(orRuleCommunication);

        rulesDependentOfIsUnit.add(new CheckUnitMP());
        rulesDependentOfIsUnit.add(new CheckIsEmptyPath());

        dependentIsUnit.add(rulesDependentOfIsUnit);
        rulesDependentOfOnBoard.add(dependentIsUnit);
        rulesDependentOfOnBoard.add(new CheckIsPriorityUnit());
        rulesDependentOfOnBoard.add(new CheckCanMoveUnit());

        dependentOnBoard.add(rulesDependentOfOnBoard);
        super.add(dependentOnBoard);
    }


    public void applyResult(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        state.removePriorityUnit(src);

        state.setUnitHasMoved(src);
        //state.updateUnitPosition(src, target);
        state.removeOneAction();
        state.moveUnit(src.getX(), src.getY(), target.getX(), target.getY());

        EUnitData movingUnit = state.getUnitType(target.getX(), target.getY());
        if(movingUnit.isCanAttack()) {
            List<Building> buildings = state.getAllBuildings();
            Building remove = null;
            for (Building building : buildings) {
                if (building.getPlayer() != action.getPlayer()
                        && building.getBuildingData() == EBuildingData.ARSENAL
                        && building.getX() == target.getX()
                        && building.getY() == target.getY()) {
                    remove = building;
                    break;
                }
            }
            if (remove != null)
                state.removeBuilding(remove);
        }
    }
}
