package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;


/**
 * Composite to be extended by super-set of atomic rules. Check the validity of the rule added by {@link RuleCompositeNOT#add(IRule)}
 * This composite represents the logical NOT for its rule.It checks the result of the rule and
 * return the opposite boolean.
 * @see RuleComposite
 * @see IRule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
 */
public class RuleCompositeNOT extends RuleComposite {
    private IRule rule;

    public RuleCompositeNOT(){
        super();
        rule = null;
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if(rule == null)
            return true;
        RuleResult tmpResult = new RuleResult();
        boolean valid = rule.checkAction(state, action, tmpResult);
        if(valid){
            result.invalidate();
            result.addMessage(rule.getRules(), "Expected false, but returned true.");
        }
        return !valid;
    }

    @Override
    public void add(IRule rule){
        this.rule = rule;
    }


    public String toString(){
        if(this.rule == null){
            return "";
        }

        return "(NOT " +
                this.rule.toString() + ")";
    }

}
