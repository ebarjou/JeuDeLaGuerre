package ruleEngine.rules;

import game.Game;
import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.*;

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
    public void applyResult(Board board, IGameState state, GameAction action, RuleResult result) {
        //System.out.println("ApplyResulMove");
        Coordinates src = action.getSourceCoordinates();
        Coordinates target = action.getTargetCoordinates();
        state.removePriorityUnit(src);
        try {
            state.getLastUnitMoved().setCanAttack(false);
        } catch (NullPointerException ignored){}

        state.addUnitMoved(src);
        state.updateUnitPosition(board.getUnitPlayer(src.getX(), src.getY()), src, target);
        state.removeOneAction();
        state.getBoard().moveUnit(src.getX(), src.getY(), target.getX(), target.getY());

    }

    public static MasterRule getInstance() {
        if (instance == null)
            instance = new MoveRules();

        return instance;
    }
}
