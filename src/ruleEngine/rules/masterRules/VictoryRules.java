package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckIsNoArsenalEnemy;
import ruleEngine.rules.atomicRules.CheckIsNoEnemy;
import ruleEngine.rules.atomicRules.CheckPlayerTurn;
import ruleEngine.rules.newRules.IRule;
import ruleEngine.rules.newRules.RuleCompositeAnd;
import ruleEngine.rules.newRules.RuleCompositeOrDep;

public class VictoryRules extends RuleCompositeAnd {
    public VictoryRules(){
        super.add(new CheckPlayerTurn());
        //TODO: The next rule has the test of the commented rule
        IRule orDep = new RuleCompositeOrDep();
        orDep.add(new CheckIsNoArsenalEnemy());
        orDep.add(new CheckIsNoEnemy());
        super.add(orDep);
    }

    public boolean checkAction(GameState state, GameAction action, RuleResult result){
        RuleResult tmpResult = new RuleResult();
        boolean validAction = super.checkAction(state, action, tmpResult);
        if(!tmpResult.isValid()){
            result.invalidate();
        } else {
            result.addMessage("", action.getPlayer() + " winner !");
        }
        return validAction;
    }

    public void applyResult(GameState state, GameAction action, RuleResult result) {
    }
}
