package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

import java.util.List;

public class CheckNoPriorityUnitAlly implements IRule {

    private boolean isPlayerHasPriorityUnits(List<Unit> priorityUnits, EPlayer player) {
        for(Unit unit : priorityUnits)
            if(unit.getPlayer() == player)
                return true;
        return false;
    }

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        if(!isPlayerHasPriorityUnits(state.getPriorityUnits(), state.getActualPlayer()))
            return true;
        result.invalidate();
        result.addMessage(this, "There is at least one priority unit.");
        return false;
    }
}
