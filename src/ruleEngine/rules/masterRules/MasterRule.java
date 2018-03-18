package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.*;

/**
 * Object to be extended by super-set of atomic rules. Check the validity of all rules added by {@link MasterRule#addRule(Rule)} and allows
 * the use of rule dependencies if those are not respected. It's recommended to use instead of checking atomic rules directly
 * on the {@link ruleEngine.RuleChecker}.
 * @see Rule
 * @see RuleResult
 * @see ruleEngine.RuleChecker
 */
public abstract class MasterRule extends Rule {
    private List<Rule> rules;
    private List<Dependency> dependencies;

    MasterRule(){
        rules = new LinkedList<>();
        dependencies = new LinkedList<>();
    }

    /**
     * Add a rule to the master rule. It will be checked every time {@link MasterRule#checkAction(GameState, GameAction, RuleResult)} is called on the master rule.
     * @param rule The atomic to check.
     */
    void addRule(Rule rule){
        rules.add(rule);
    }

    /**
     * Add a dependency to a given rule. If {@code dependency} does not give the {@code expectedState} specified here, {@code rule}
     * will not be checked.
     *
     * Because of the complex architecture the engine would require for a solid dependency system, this feature is not fully implemented
     * and thus should not be used.
     * @param rule The rule dependent of another.
     * @param dependency The rule from which the first one is dependent.
     * @param expectedState The expected answer form {@code dependency} for {@code rule} to be checked.
     */
    @Deprecated
    void addDependence(Rule rule, Rule dependency, boolean expectedState){
        dependencies.add(new Dependency(rule, dependency, expectedState));
    }

    /**
     * The modification of the game to be applied if all rules are respected.
     * @param state The state of the game to be updated.
     * @param action The allowed action on the game and thus to be performed.
     * @param result Information to give to the game once the action is performed.
     */
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
