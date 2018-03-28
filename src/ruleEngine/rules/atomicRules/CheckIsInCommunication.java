package ruleEngine.rules.atomicRules;

import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

public class CheckIsInCommunication implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        try {
            if (state.isInCommunication(action.getPlayer(), src.getX(), src.getY()))
                return true;
        } catch (IllegalBoardCallException ignored) {
        }

        result.addMessage(this, "This unit is not in the player communication.");
        result.invalidate();
        return false;
    }
}
