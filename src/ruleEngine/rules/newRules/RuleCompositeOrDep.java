package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

public class RuleCompositeOrDep extends AbsRuleComposite {

    //private List<IRule> rules;

    public RuleCompositeOrDep(){
        super();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if(rules.isEmpty())
            return true;
        boolean valid = false;
        RuleResult tmpResult = new RuleResult();
        for(IRule rule : rules){

            valid = rule.checkAction(state, action, tmpResult);
            if(valid) {
                return true;
            }
        }

        result.addMessage(tmpResult.getLogMessage());
        result.invalidate();
        return valid;
    }

    public String toString(){
        if(rules.size() == 0){
            return this.getClass().getSimpleName();
        }
        StringBuilder str = new StringBuilder();
        str.append("(");
        for(int i = 0; i < rules.size() - 1; i++){
            str.append(rules.get(i).toString()).append(" ORdep ");
        }
        str.append(rules.get(rules.size()-1).toString()).append(")");

        return str.toString();
    }
}
