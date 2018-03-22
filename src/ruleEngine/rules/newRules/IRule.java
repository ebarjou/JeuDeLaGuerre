package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

public interface IRule {
    boolean checkAction(GameState state, GameAction action, RuleResult result);
    String toString();
    default String getRules(){
      return toString();
    }
    default void applyResult(GameState state, GameAction action, RuleResult result){}
    default void add(IRule r){}
}
