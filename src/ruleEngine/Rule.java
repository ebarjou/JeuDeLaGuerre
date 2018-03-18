package ruleEngine;

import game.gameState.GameState;

/**
 * Class to be extended by any rule of the game.
 * @see RuleChecker
 * @see ruleEngine.rules.masterRules.MasterRule
 */
public abstract class Rule {
    /**
     * Method called by the RuleChecker or its master rules. Should check if a given action on a given stage of a game is legal or not.
     * @param state The stage of the game when the action is performed.
     * @param action The action to check the validity on.
     * @param result The object to be used to set the violation of the rule and to add information about the checked action.
     * @return true if the rule is respected, false otherwise.
     */
    public abstract boolean checkAction(GameState state, GameAction action, RuleResult result);

    @Override
    public boolean equals(Object obj) {
        return this.getClass().getName().equals(obj.getClass().getName());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
