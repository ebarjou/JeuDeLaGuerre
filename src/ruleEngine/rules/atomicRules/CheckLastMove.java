package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckLastMove extends Rule {
    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Unit lastMove;
        try {
            lastMove = state.getLastUnitMoved();
        } catch (NullPointerException e) {
            //TODO: stage.getLastUnitMoved() does not throw any exception
            result.invalidate();
            result.addMessage(this, "No unit has been moved yet.");
            return false;
        }

        if ((lastMove.getX() != src.getX()) || (lastMove.getY() != src.getY())) {
            result.invalidate();
            result.addMessage(this, "Unit is not the last one moved.");
            return false;
        }

        return true;
    }
}
