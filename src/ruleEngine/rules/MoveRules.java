package ruleEngine.rules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import game.gameMaster.GameMaster;
import game.gameMaster.GameState;
import ruleEngine.rules.atomicRules.*;

public class MoveRules implements IRule {

    private RuleList rules;
    private static IRule instance;

    private MoveRules() {
        //TODO: Put here the sub-rules (atomic) you need to check.
        /*rules.add(CheckPlayerTurn.getInstance());   //Maybe use an enum to get rid of getInstance()'s through implicit getValue() ? Or use static classes ? dunno
        rules.add(CheckOnBoard.getInstance());
        rules.add(CheckIsUnit.getInstance());
        rules.add(CheckCommunication.getInstance());
        rules.add(CheckUnitMP.getInstance());
        rules.add(CheckIsEmptyPath.getInstance());
        rules.add(CheckPlayerMovesLeft.getInstance());*/

        rules = new RuleList();
        rules.add(CheckPlayerTurn.class);
        rules.add(CheckPlayerMovesLeft.class);
        rules.add(CheckCommunication.class);
        rules.add(CheckOnBoard.class);
        rules.add(CheckIsUnit.class);
        rules.add(CheckUnitMP.class);
        rules.add(CheckIsEmptyPath.class);
    }

    public static IRule getInstance() {
        if (instance == null)
            instance = new MoveRules();

        return instance;
    }

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        boolean valid = true;
        for (IRule r : rules)
            valid = valid && r.checkAction(board, GameMaster.getInstance().getActualState(), action, result);

        return valid;
    }
}
