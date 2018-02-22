package ruleEngine.rules;

import game.Game;
import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class MasterRule implements IRule {
    private RuleList rules;
    private Map<IRule, IRule> dependances;

    MasterRule(){
        rules = new RuleList();
        dependances = new HashMap<>();
    }

    void addRule(Class<? extends IRule> rule){
        rules.add(rule);
    }

    void addDependantRule(Class<? extends IRule> rule, Class<? extends IRule> dependance){
        addRule(rule);
        dependances.put(RuleManager.getInstance().getRule(rule), RuleManager.getInstance().getRule(dependance));
    }

    abstract void applyResult(Board board, GameState state, GameAction action, RuleResult result);

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        List<IRule> invalidateRules = new LinkedList<>();
        for (IRule r : rules){
            if (dependances.containsKey(r) && invalidateRules.contains(dependances.get(r))){
                result.addMessage(this, r.getClass().getSimpleName() + " is not checked because it is dependant of " + dependances.get(r).getClass().getSimpleName() + "'s success.");
                result.invalidate();
            }else{
                if (!r.checkAction(board, Game.getInstance().getGameState(), action, result))
                    invalidateRules.add(r);
            }
        }

        if (result.isValid())
            applyResult(state.getBoard(), Game.getInstance().getGameStateManager(), action, result);

        return result.isValid();
    }

}
