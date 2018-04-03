package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import java.util.LinkedList;
import java.util.List;

/**
 * Object to be extended by Composite of IRules. Check the validity of all rules added by {@link RuleComposite#add(IRule)} and allows
 * the use of rule dependencies if those are not respected. It's recommended to use instead of checking atomic rules directly
 * on the {@link ruleEngine.RuleChecker}.
 *
 * @see IRule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
 */
public abstract class RuleComposite implements IRule {

    protected final List<IRule> rules;

    public RuleComposite() {
        rules = new LinkedList<>();
    }

    @Override
    public abstract boolean checkAction(GameState state, GameAction action, RuleResult result);

    /**
     * The modification of the game to be applied if all rules are respected.
     *
     * @param state  The state of the game to be updated.
     * @param action The allowed action on the game and thus to be performed.
     * @param result Information to give to the game once the action is performed.
     */


    public void applyResult(GameState state, GameAction action, RuleResult result) {

    }

    /**
     * @return a String containing all the class name of each atomic rule in this composite
     */
    @Override
    public String getRules() {
        if (rules.isEmpty())
            return "";

        StringBuilder rulesName = new StringBuilder();
        for (int i = 0; i < rules.size() - 1; ++i)
            rulesName.append(rules.get(i).getRules()).append(", ");

        rulesName.append(rules.get(rules.size() - 1).getRules());
        return rulesName.toString();
    }

    /**
     * Add a rule to the master rule. It will be checked every time {@link RuleComposite#checkAction(GameState, GameAction, RuleResult)} is called on the master rule.
     *
     * @param rule The atomic to check.
     */
    public void add(IRule rule) {
        rules.add(rule);
    }
}
