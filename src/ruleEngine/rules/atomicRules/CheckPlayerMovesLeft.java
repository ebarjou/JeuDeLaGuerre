package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckPlayerMovesLeft implements IRule {

    @Override
    public boolean checkAction(IBoard board, GameState state, GameAction action, RuleResult result) {
        if (state.getActionLeft() < 1) {
            result.invalidate();
            result.addMessage(this, "This player has no action left this turn.");
            return false;
        }

        return true;
    }
}
