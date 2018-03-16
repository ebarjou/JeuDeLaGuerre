package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.*;

public abstract class MasterRule extends Rule {
    private List<Rule> rules;
    private List<Dependency> dependencies;

    MasterRule(){
        rules = new LinkedList<>();
        dependencies = new LinkedList<>();
    }

    void addRule(Rule rule){
        rules.add(rule);
    }

    void addDependence(Rule rule, Rule dependency, boolean expectedState){
        dependencies.add(new Dependency(rule, dependency, expectedState));
    }

    abstract public void applyResult(GameState state, GameAction action, RuleResult result);

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Map<Rule, Boolean> ruleState = new HashMap<>();
        for (Rule r : rules){
            List<Dependency> deps = new LinkedList<>();
            for(Dependency d : dependencies)
                if (d.getClient().equals(r))
                    deps.add(d);

            List<Dependency> irs = new LinkedList<>();
            for(Dependency d : deps)
                if (!ruleState.containsKey(d.getDependency()) || ruleState.get(d.getDependency()) != d.getExpectedState())
                    irs.add(d);

            if (irs.size() > 0){
                StringBuilder sb = new StringBuilder();
                for(Dependency dep : irs)
                    sb.append("\t- " + dep.getDependency().getClass().getSimpleName())
                            .append(" : expected ")
                            .append((dep.getExpectedState() ? "Valid" : "Invalid"))
                            .append(" but ")
                            .append((!ruleState.containsKey(dep.getDependency()) ? "was not checked" : "got " + (dep.getExpectedState() ? "Invalid" : "Valid") + " instead"))
                            .append(".\n");

                result.addMessage(this, r.getClass().getSimpleName() + " is not checked because it is dependant of the following rule(s) : \n" + sb.toString());
                result.invalidate();
            }else{
                ruleState.put(r, r.checkAction(state, action, result));
            }
        }

        return result.isValid();
    }
}
