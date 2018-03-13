package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckOnBoard implements IRule {

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
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
