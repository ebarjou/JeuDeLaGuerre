package ruleEngine.rules.atomicRules;

import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckPlayerMovesLeft extends Rule {

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        if (state.getActionLeft() < 1) {
            result.invalidate();
            result.addMessage(this, "This player has no action left this turn.");
            return false;
        }

        return true;
    }
}
