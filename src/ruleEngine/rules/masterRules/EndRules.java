package ruleEngine.rules.masterRules;

import game.board.Board;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckNoPriorityUnitAlly;

public class EndRules extends MasterRule {

    public EndRules(){
        addRule(new CheckNoPriorityUnitAlly());
    }

    @Override
    public void applyResult(Board board, GameState state, GameAction action, RuleResult result) {
        state.switchPlayer();
    }
}
