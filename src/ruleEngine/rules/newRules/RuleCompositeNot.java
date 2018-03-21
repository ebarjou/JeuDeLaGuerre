package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

public class RuleCompositeNot extends AbsRuleComposite {
    private IRule rule;

    public RuleCompositeNot(){
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
        //return !rule.checkAction(state, action, result);
    }

    @Override
    public void add(IRule rule){
        this.rule = rule;
    }

}
