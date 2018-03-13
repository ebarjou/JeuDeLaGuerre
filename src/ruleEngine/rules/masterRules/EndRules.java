package ruleEngine.rules.masterRules;

import game.board.Board;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckNoPriorityUnitAlly;

public class EndRules extends MasterRule {

    public EndRules(){
        addRule(new CheckNoPriorityUnitAlly());
    }

    @Override
    public void applyResult(GameState state, GameAction action, RuleResult result) {
        state.switchPlayer();
    }
}
