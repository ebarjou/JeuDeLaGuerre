package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.List;

/**
 * Check if there's no unit that need to retreat before ending the turn.<br>
 * Valid if there is no units that need to retreat, invalid otherwise.
 * @see ruleEngine.rules.masterRules.EndRules
 */
public class CheckNoPriorityUnitAlly extends Rule {

    private boolean isPlayerHasPriorityUnits(List<Unit> priorityUnits, EPlayer player) {
        for(Unit unit : priorityUnits)
            if(unit.getPlayer() == player)
                return true;
        return false;
    }

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        if(!isPlayerHasPriorityUnits(state.getPriorityUnits(), state.getActualPlayer()))
            return true;
        result.invalidate();
        result.addMessage(this, "There is at least one priority unit.");
        return false;
    }
}
