package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckIsNoArsenalEnemy;
import ruleEngine.rules.atomicRules.CheckIsNoEnemy;
import ruleEngine.rules.atomicRules.CheckPlayerTurn;
import ruleEngine.rules.newRules.IRule;
import ruleEngine.rules.newRules.RuleCompositeAND;
import ruleEngine.rules.newRules.RuleCompositeLazyOR;

/**
 * Class testing if a game is ended by the destruction of all of a player units or arsenals.
 */
public class VictoryRules extends RuleCompositeAND {

    public VictoryRules() {
        super.add(new CheckPlayerTurn());
        IRule orDep = new RuleCompositeLazyOR();
        orDep.add(new CheckIsNoArsenalEnemy());
        orDep.add(new CheckIsNoEnemy());
        super.add(orDep);
    }

    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        RuleResult tmpResult = new RuleResult();
        boolean validAction = super.checkAction(state, action, tmpResult);
        if (!tmpResult.isValid())
            result.invalidate();
        else
            result.addMessage(this, action.getPlayer() + " winner !");

        return validAction;
    }

    public void applyResult(GameState state, GameAction action, RuleResult result) {
    }
}
