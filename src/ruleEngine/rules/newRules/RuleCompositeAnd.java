package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

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