package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

/**
 * Check if the action is performed by the right player on this turn.<br>
 * Valid if the action's author is the same as the current player's turn, invalid otherwise.
 * @see ruleEngine.rules.masterRules.MoveRules
 * @see ruleEngine.rules.masterRules.AttackRules
 * @see ruleEngine.rules.masterRules.EndRules
 * @see ruleEngine.rules.masterRules.VictoryRules
 */
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
