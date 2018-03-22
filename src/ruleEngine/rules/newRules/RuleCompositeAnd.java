package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

/**
 * Composite to be extended by super-set of atomic rules. Check the validity of all rules added
 * by {@link AbsRuleComposite#add(IRule)}. This composite represents the logical AND between
 * all IRule in {@link AbsRuleComposite#rules}, it checks all the rules even if the result is
 * invalidate by one of the rule because we want to have the maximum of log on why the action
 * is incorrect. If you need the lazy AND, you must see {@link RuleCompositeAndDep}.
 * @see AbsRuleComposite
 * @see IRule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
*/
public class RuleCompositeAnd extends AbsRuleComposite {

    public RuleCompositeAnd(){
        super();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        boolean valid = true;
        for(IRule rule : rules){
            valid = rule.checkAction(state, action, result) && valid;
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
            str.append(rules.get(i).toString()).append(" AND ");
        }
        str.append(rules.get(rules.size()-1).toString()).append(")");

        return str.toString();
    }

}
