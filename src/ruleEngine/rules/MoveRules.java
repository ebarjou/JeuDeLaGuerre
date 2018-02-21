package ruleEngine.rules;

import game.Game;
import game.board.IBoard;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.*;

public class MoveRules extends MasterRule {

    private static IRule instance;

    private MoveRules() {
        //TODO: Put here the sub-rules (atomic) you need to check.
        addRule(CheckPlayerTurn.class);
        addRule(CheckPlayerMovesLeft.class);
        addRule(CheckCommunication.class);
        addRule(CheckOnBoard.class);
        addRule(CheckIsUnit.class);
        //addRule();(CheckIsPriorityUnit.class);
        addDependantRule(CheckUnitMP.class, CheckIsUnit.class);
        addRule(CheckIsEmptyPath.class);
    }

    @Override
    void applyResult(IBoard board, GameState state, GameAction action, RuleResult result) {
        //TODO: Apply move on Board here
    }

    public static IRule getInstance() {
        if (instance == null)
            instance = new MoveRules();

        return instance;
    }
}
