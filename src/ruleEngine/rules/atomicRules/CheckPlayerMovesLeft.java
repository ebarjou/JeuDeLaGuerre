package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

public class CheckPlayerMovesLeft implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if (state.getActionLeft() < 1) {
            result.invalidate();
            result.addMessage(this, "This player has no action left this turn.");
            return false;
        }

        return true;
    }



    public String toString(){
        return this.getClass().getSimpleName();
    }

}
