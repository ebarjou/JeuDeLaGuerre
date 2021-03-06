package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

/**
 * Composite to be extended by super-set of atomic rules. Check the validity of all rules added
 * by {@link RuleComposite#add(IRule)}. This composite represents the logical lazy AND between
 * all IRule in {@link RuleComposite#rules}, it checks all the rules while the result is valid
 * and stop when a rule fail. The order in which you add IRule to rules is important.
 *
 * @see RuleComposite
 * @see IRule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
 */
public class RuleCompositeLazyAND extends RuleComposite {

    public RuleCompositeLazyAND() {
        super();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        boolean valid = rules.isEmpty();
        if (valid)
            return true;

        IRule failedRule = null;
        valid = true;
        for (IRule rule : rules) {
            if (valid) {
                valid = rule.checkAction(state, action, result);
                if (!valid)
                    failedRule = rule;
            } else {
                result.addMessage(rule.getRules(),
                        "Those rules are not checked because "
                                + failedRule.getClass().getSimpleName() + " has failed.");
            }
        }
        return valid;
    }

    public String getName() {
        if (rules.isEmpty())
            return this.getClass().getSimpleName();

        StringBuilder str = new StringBuilder();
        str.append("(");
        for (int i = 0; i < rules.size() - 1; ++i)
            str.append(rules.get(i).getName()).append(" lazyAND ");

        str.append(rules.get(rules.size() - 1).getName()).append(")");

        return str.toString();
    }
}
