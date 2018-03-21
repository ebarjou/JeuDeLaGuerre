package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

public class CheckPlayerTurn implements IRule {

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


    public String toString(){
        return this.getClass().getSimpleName();
    }
}
