package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

/**
 * Composite to be extended by super-set of atomic rules. Check the validity of all rules added
 * by {@link AbsRuleComposite#add(IRule)}. This composite represents the logical lazy AND between
 * all IRule in {@link AbsRuleComposite#rules}, it checks all the rules while the result is valid
 * and stop when a rule fail. The order in which you add IRule to rules is important.
 * @see AbsRuleComposite
 * @see IRule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
 */
public class RuleCompositeAndDep extends AbsRuleComposite {

    public RuleCompositeAndDep(){
        super();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        boolean valid = rules.isEmpty();
        if(valid)
            return true;

        IRule failedRule = null;
        valid = true;
        for(IRule rule : rules) {
            if(valid) {
                valid = rule.checkAction(state, action, result);
                if(!valid)
                    failedRule = rule;
            }else{
                result.addMessage(rule.getRules(),
                        "Those rules are not checked because "
                                    + failedRule.getClass().getSimpleName() + " has failed.");
            }
        }
        return valid;
    }

    public String toString(){
        if(rules.size() == 0){
            return this.getClass().getSimpleName();
        }
        StringBuilder str = new StringBuilder();
        str.append("(");
        for(int i = 0; i < rules.size() - 1; i++){
            str.append(rules.get(i).toString()).append(" ANDdep ");
        }
        str.append(rules.get(rules.size()-1).toString()).append(")");

        return str.toString();
    }
}
