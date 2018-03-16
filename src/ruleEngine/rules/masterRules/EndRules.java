package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckNoPriorityUnitAlly;
import ruleEngine.rules.atomicRules.CheckPlayerTurn;

public class EndRules extends MasterRule {

    public EndRules(){
        addRule(new CheckPlayerTurn());
        addRule(new CheckNoPriorityUnitAlly());
    }

    @Override
    public void applyResult(GameState state, GameAction action, RuleResult result) {
        state.switchPlayer();
    }
}
