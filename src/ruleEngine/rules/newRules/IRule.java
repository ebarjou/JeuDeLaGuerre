package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

public interface IRule {
    boolean checkAction(GameState state, GameAction action, RuleResult result);

    default String getName() {
        return getClass().getSimpleName();
    }

    default String getRules() {
        return getName();
    }

}
