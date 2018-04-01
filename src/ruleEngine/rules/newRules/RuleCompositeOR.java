package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

/**
 * Composite to be extended by super-set of atomic rules. Check the validity of all rules added
 * by {@link RuleComposite#add(IRule)}. This composite represents the logical OR between
 * all IRule in {@link RuleComposite#rules}, it checks all the rules for the same reasons as
 * {@link RuleCompositeAND}. If you need the lazy OR, you must check {@link RuleCompositeOR}.
 *
 * @see RuleComposite
 * @see IRule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
 */
public class RuleCompositeOR extends RuleComposite {

    public RuleCompositeOR() {
        super();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if (rules.isEmpty())
            return true;
        boolean valid = false;
        RuleResult tmpResult = new RuleResult();
        for (IRule rule : rules) {
            valid = rule.checkAction(state, action, tmpResult) || valid;
        }
        if (!valid) {
            result.invalidate();
            result.addMessage(tmpResult.getLogMessage());
        }
        return valid;
    }

    public String getName() {
        if (rules.isEmpty())
            return this.getClass().getSimpleName();

        StringBuilder str = new StringBuilder();
        str.append("(");
        for (int i = 0; i < rules.size() - 1; ++i)
            str.append(rules.get(i).getName()).append(" OR ");

        str.append(rules.get(rules.size() - 1).getName()).append(")");

        return str.toString();
    }
}
