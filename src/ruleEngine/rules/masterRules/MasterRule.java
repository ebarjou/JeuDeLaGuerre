package ruleEngine.rules.masterRules;

import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

import java.util.*;

public abstract class MasterRule implements IRule {
    private List<IRule> rules;
    private Map<IRule, Set<IRule>> dependencies;

    MasterRule(){
        rules = new LinkedList<>();
        dependencies = new HashMap<>();
    }

    void addRule(IRule rule){
        rules.add(rule);
    }

    void addDependence(IRule rule, IRule... dependence){
        if (!dependencies.containsKey(rule))
            dependencies.put(rule, new HashSet<>());
        Collections.addAll(dependencies.get(rule), dependence);
    }

    abstract public void applyResult(Board board, GameState state, GameAction action, RuleResult result);

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        List<IRule> invalidateRules = new LinkedList<>();
        for (IRule r : rules){
            Set<IRule> irs = new HashSet<>(invalidateRules);
            if (dependencies.containsKey(r))
                irs.retainAll(dependencies.get(r));
            if (irs.size() > 0){
                StringBuilder sb = new StringBuilder();
                for(IRule dep : irs)
                    sb.append(dep.getClass().getSimpleName()).append(", ");

                sb.delete(sb.toString().length() - 3, sb.toString().length() - 1);
                result.addMessage(this, r.getClass().getSimpleName() + " is not checked because it is dependant of " + sb.toString() + "'s success.");
                result.invalidate();
            }else{
                if (!r.checkAction(board, state, action, result))
                    invalidateRules.add(r);
            }
        }

        return result.isValid();
    }
}
