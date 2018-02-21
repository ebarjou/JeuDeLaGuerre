package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckIsPriorityUnit implements IRule {
    @Override
    public boolean checkAction(IBoard board, GameState state, GameAction action, RuleResult result) {
        if (state.isPriorityCoord(action.getSourceCoordinates()))
            return true;
        result.addMessage(this, "Other unit need to be moved first.");
        return false;
    }
}
