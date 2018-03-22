package ruleEngine.rules.newRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import java.util.LinkedList;
import java.util.List;

/**
 - * Object to be extended by super-set of atomic rules. Check the validity of all rules added by {@link AbsRuleComposite#add(IRule)} and allows
 - * the use of rule dependencies if those are not respected. It's recommended to use instead of checking atomic rules directly
 - * on the {@link ruleEngine.RuleChecker}.
 - * @see IRule
 - * @see RuleResult
 - * @see ruleEngine.RuleChecker
 - */
public abstract class AbsRuleComposite implements IRule{

    protected List<IRule> rules;

    public AbsRuleComposite(){
        rules = new LinkedList<>();
    }

    @Override
    public abstract boolean checkAction(GameState state, GameAction action, RuleResult result);

    /**
      * The modification of the game to be applied if all rules are respected.
      * @param state The state of the game to be updated.
      * @param action The allowed action on the game and thus to be performed.
      * @param result Information to give to the game once the action is performed.
      */
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

    /**
      * Add a rule to the master rule. It will be checked every time {@link AbsRuleComposite#checkAction(GameState, GameAction, RuleResult)} is called on the master rule.
      * @param rule The atomic to check.
      */
    @Override
    public void add(IRule rule) {
        rules.add(rule);
    }
}
