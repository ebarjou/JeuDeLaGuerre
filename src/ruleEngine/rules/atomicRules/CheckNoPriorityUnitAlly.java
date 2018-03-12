package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckNoPriorityUnitAlly implements IRule {
    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        if(!state.isPriorityUnitPlayer(state.getActualPlayer()))
            return true;
        result.invalidate();
        result.addMessage(this, "There is at least one priority unit.");
        return false;
    }
}
