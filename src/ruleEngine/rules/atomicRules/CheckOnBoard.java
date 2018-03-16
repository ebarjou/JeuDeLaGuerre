package ruleEngine.rules.atomicRules;

import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckOnBoard extends Rule {

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
}
