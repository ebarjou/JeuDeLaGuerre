package ruleEngine.rules;

import ruleEngine.IRule;

import java.util.HashMap;
import java.util.Map;

public class RuleManager {
    private static Map<Class<? extends IRule>, IRule> rules;

    private static RuleManager instance;

    private RuleManager() {
        rules = new HashMap<>();
    }

    public static RuleManager getInstance() {
        if (instance == null)
            instance = new RuleManager();
        return instance;
    }

    public IRule getRule(Class<? extends IRule> rule) {
        if (!rules.containsKey(rule)) {
            try {
                rules.put(rule, rule.newInstance());
            } catch (IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        }

        return rules.get(rule);
    }
}
