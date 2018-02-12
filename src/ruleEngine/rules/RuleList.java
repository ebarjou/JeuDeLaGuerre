package ruleEngine.rules;

import ruleEngine.IRule;

import java.util.LinkedList;
import java.util.List;

public class RuleList extends LinkedList<IRule> {

    public void add(Class<? extends IRule> rule){
        super.add(RuleManager.getInstance().getRule(rule));
    }
}
