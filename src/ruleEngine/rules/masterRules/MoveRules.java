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

/**
 * Class testing if a unit move is allowed according to its range of movement, the terrain and its communication supplying.
 * Performs the move on the board if respected.
 */
public class MoveRules extends RuleCompositeAND {

    public MoveRules() {
        super.add(new CheckPlayerTurn());
        super.add(new CheckPlayerMovesLeft());

        IRule dependentOnBoard = new RuleCompositeLazyAND();
        dependentOnBoard.add(new CheckOnBoard());

        IRule rulesDependentOfOnBoard = new RuleCompositeAND();

        IRule dependendentIsUnit = new RuleCompositeLazyAND();
        dependendentIsUnit.add(new CheckIsAllyUnit());

        IRule rulesDependentOfIsUnit = new RuleCompositeAND();

        IRule orRuleCommunication = new RuleCompositeLazyOR();
        orRuleCommunication.add(new CheckIsInCommunication());
        orRuleCommunication.add(new CheckIsRelay());
        rulesDependentOfIsUnit.add(orRuleCommunication);

        rulesDependentOfIsUnit.add(new CheckUnitMP());
        rulesDependentOfIsUnit.add(new CheckIsEmptyPath());
        rulesDependentOfIsUnit.add(new CheckIsPriorityUnit());
        rulesDependentOfIsUnit.add(new CheckCanMoveUnit());

        dependendentIsUnit.add(rulesDependentOfIsUnit);
        rulesDependentOfOnBoard.add(dependendentIsUnit);

        dependentOnBoard.add(rulesDependentOfOnBoard);
        super.add(dependentOnBoard);
    }


    public void applyResult(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        state.removePriorityUnit(src);

        state.setUnitHasMoved(src);
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
            if (remove != null) {
                state.removeBuilding(remove);
                // Destroying an ARSENAL is considered an attack. The moving unit is therefore unable to initiate another attack
                state.setLastUnitMoved(null);
                result.addMessage(this, "The Arsenal at position (" + target.getX() + ", " + target.getY() + ") has been destroyed.");
            }
        }
    }
}
