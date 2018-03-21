package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckNoPriorityUnitAlly;
import ruleEngine.rules.atomicRules.CheckPlayerTurn;
import ruleEngine.rules.newRules.RuleCompositeAnd;

public class EndRules extends RuleCompositeAnd {

    public EndRules(){
        super.add(new CheckPlayerTurn());
        super.add(new CheckNoPriorityUnitAlly());
    }

    public void applyResult(GameState state, GameAction action, RuleResult result) {
        state.switchPlayer();
    }
}
