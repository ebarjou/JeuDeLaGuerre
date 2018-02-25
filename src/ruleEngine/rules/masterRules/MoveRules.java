package ruleEngine.rules.masterRules;

import game.board.Board;
import game.board.Unit;
import game.gameMaster.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.*;

import java.util.List;

public class MoveRules extends MasterRule {

    private static MasterRule instance;

    private MoveRules() {
        //TODO: Put here the sub-rules (atomic) you need to check.
        addRule(CheckPlayerTurn.class);
        addRule(CheckPlayerMovesLeft.class);
        addRule(CheckCommunication.class);
        addRule(CheckOnBoard.class);
        addRule(CheckIsAllyUnit.class);
        addRule(CheckIsPriorityUnit.class);
        addRule(CheckCanMoveUnit.class);
        addDependantRule(CheckUnitMP.class, CheckIsAllyUnit.class);
        addRule(CheckIsEmptyPath.class);
    }

    @Override
    public void applyResult(Board board, GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        state.removePriorityUnit(src);
        List<Unit> list = state.getAllUnits();
        for(Unit u : list){
            if(u.getX() == src.getX() && u.getY() == src.getY())
                System.out.println(u.toString());
        }
        try {
            state.getLastUnitMoved().setCanAttack(false);
        } catch (NullPointerException ignored){}

        state.setUnitHasMoved(src);
        state.updateUnitPosition(src, target);
        state.removeOneAction();
        state.getMutableBoard().moveUnit(src.getX(), src.getY(), target.getX(), target.getY());

    }

    public static MasterRule getInstance() {
        if (instance == null)
            instance = new MoveRules();

        return instance;
    }
}
