package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

/**
 * Check if the action is performed by the right player on this turn.<br>
 * Valid if the action's author is the same as the current player's turn, invalid otherwise.
 * @see ruleEngine.rules.masterRules.MoveRules
 * @see ruleEngine.rules.masterRules.AttackRules
 * @see ruleEngine.rules.masterRules.EndRules
 * @see ruleEngine.rules.masterRules.VictoryRules
 */
public class CheckPlayerTurn implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if (action.getPlayer() != state.getActualPlayer()) {
            result.invalidate();
            result.addMessage(this, "This is not player " + action.getPlayer().toString() + "'s turn.");
            return false;
        }

        return true;
    }
}
