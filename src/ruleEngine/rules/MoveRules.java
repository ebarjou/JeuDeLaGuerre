package ruleEngine.rules;

import game.Game;
import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameMaster;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.*;

public class MoveRules implements IRule {

    private static IRule instance;
    private RuleList rules;

    private MoveRules() {
        //TODO: Put here the sub-rules (atomic) you need to check.
        rules = new RuleList();
        rules.add(CheckPlayerTurn.class);
        rules.add(CheckPlayerMovesLeft.class);
        rules.add(CheckCommunication.class);
        rules.add(CheckOnBoard.class);
        rules.add(CheckIsUnit.class);
        //rules.add(CheckIsPriorityUnit.class);
        rules.add(CheckUnitMP.class);
        rules.add(CheckIsEmptyPath.class);
    }

    public static IRule getInstance() {
        if (instance == null)
            instance = new MoveRules();

        return instance;
    }

    @Override
    public boolean checkAction(IBoard board, GameState state, GameAction action, RuleResult result) {
        for (IRule r : rules)
            r.checkAction(board, Game.getInstance().getGameMaster().getActualState(), action, result);

        return result.isValid();
    }
}
