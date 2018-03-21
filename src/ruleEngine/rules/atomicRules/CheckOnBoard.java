package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

public class CheckOnBoard implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if (!state.isValidCoordinate(action.getSourceCoordinates().getX(), action.getSourceCoordinates().getY())) {
            result.addMessage(this, "Source coordinates are beyond the board's edges.");
            result.invalidate();
            return false;
        }
        if (!state.isValidCoordinate(action.getTargetCoordinates().getX(), action.getTargetCoordinates().getY())) {
            result.addMessage(this, "Target coordinates are beyond the board's edges.");
            result.invalidate();
            return false;
        }
        return true;
    }



    public String toString(){
        return this.getClass().getSimpleName();
    }
}
