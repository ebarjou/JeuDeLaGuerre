package ruleEngine.rules.masterRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.atomicRules.CheckIsNoArsenalEnemy;
import ruleEngine.rules.atomicRules.CheckPlayerTurn;

/**
 * Class testing if a game is ended by the destruction of all of a player units or arsenals.
 */
public class VictoryRules extends MasterRule{
    public VictoryRules(){
        addRule(new CheckPlayerTurn());
        //TODO: The next rule has the test of the commented rule
        addRule(new CheckIsNoArsenalEnemy());
        //addRule(new CheckIsNoEnemy());
    }

    @Override
    public void applyResult(GameState state, GameAction action, RuleResult result) {

    }
}
