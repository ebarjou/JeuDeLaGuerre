package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckPlayerTurn extends Rule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if (action.getPlayer() != state.getActualPlayer()) {
            result.invalidate();
            //TODO: Access player's name through EPlayer enum ?
            result.addMessage(this, "This is not player " + action.getPlayer().name() + "'s turn.");
            return false;
        }

        return true;
    }
}
