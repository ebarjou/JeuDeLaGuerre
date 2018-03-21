package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import java.util.LinkedList;
import java.util.List;

public abstract class AbsRuleComposite implements IRule{

    protected List<IRule> rules;

    public AbsRuleComposite(){
        rules = new LinkedList<>();
    }

    @Override
    public abstract boolean checkAction(GameState state, GameAction action, RuleResult result);

    @Override
    public void applyResult(GameState state, GameAction action, RuleResult result){

    }

    @Override
    public String getRules(){
        StringBuilder rulesName = new StringBuilder();
        for(IRule rule : rules){
            rulesName.append(rule.getRules()).append(", ");
        }
        return rulesName.toString();
    }

    @Override
    public void add(IRule r) {
        rules.add(r);
    }
}
