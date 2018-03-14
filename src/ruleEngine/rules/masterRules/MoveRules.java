package ruleEngine.rules.masterRules;

import game.board.Board;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.*;

import java.util.List;

public class MoveRules extends MasterRule {

    public MoveRules() {
        //TODO: Put here the sub-rules (atomic) you need to check.
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
    }

    @Override
    public void applyResult(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        state.removePriorityUnit(src);
        List<Unit> list = state.getAllUnits();
        for(Unit u : list){
            if(u.getX() == src.getX() && u.getY() == src.getY())
                System.out.println(u.toString());
        }

        state.setUnitHasMoved(src);
        //state.updateUnitPosition(src, target);
        state.removeOneAction();
        state.moveUnit(src.getX(), src.getY(), target.getX(), target.getY());
    }
}
