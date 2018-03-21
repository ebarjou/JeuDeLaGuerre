package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

public class RuleCompositeOr extends AbsRuleComposite {

    public RuleCompositeOr(){
        super();
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if(rules.isEmpty())
            return true;
        boolean valid = false;
        RuleResult tmpResult = new RuleResult();
        for(IRule rule : rules){
            valid = rule.checkAction(state, action, tmpResult) || valid;
        }
        if(!valid) {
            result.invalidate();
            result.addMessage(this, tmpResult.getLogMessage());
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
            str.append(rules.get(i).toString()).append(" OR ");
        }
        str.append(rules.get(rules.size()-1).toString()).append(")");

        return str.toString();
    }
}
