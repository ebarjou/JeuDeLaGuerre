package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckIsPriorityUnit implements IRule {
    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        if (state.isUnitHasPriority(action.getSourceCoordinates()))
            return true;
        result.addMessage(this, "Other unit need to be moved first.");
        result.invalidate();
        return false;
    }
}
