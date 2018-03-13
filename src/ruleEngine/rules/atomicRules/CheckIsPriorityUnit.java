package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckIsPriorityUnit implements IRule {
    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        if (state.isUnitHasPriority(action.getSourceCoordinates()))
            return true;
        result.addMessage(this, "There are other units that need to be moved first.");
        result.invalidate();
        return false;
    }
}
