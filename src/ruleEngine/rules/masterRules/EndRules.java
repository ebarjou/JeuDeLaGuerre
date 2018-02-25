package ruleEngine.rules.masterRules;

import game.board.Board;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckNoPriorityUnitAlly;

public class EndRules extends MasterRule {

    private static MasterRule instance;

    private EndRules(){
        addRule(CheckNoPriorityUnitAlly.class);
    }

    public static MasterRule getInstance() {
        if (instance == null)
            instance = new EndRules();

        return instance;
    }

    @Override
    public void applyResult(Board board, GameState state, GameAction action, RuleResult result) {
        state.switchPlayer();
    }
}
