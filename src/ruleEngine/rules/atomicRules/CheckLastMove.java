package ruleEngine.rules.atomicRules;

import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

/**
 * Check if the unit attacking is the last one moved by the player, as a delayed attack or static attack isn't allowed from
 * our rule interpretation.<br>
 * Valid if the attacking unit is the same that has performed a move this same turn, invalid otherwise.
 * @see ruleEngine.rules.masterRules.AttackRules
 */
public class CheckLastMove extends Rule {
    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
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
