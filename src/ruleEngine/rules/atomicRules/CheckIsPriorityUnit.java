package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

import java.util.List;

public class CheckIsPriorityUnit extends Rule {
    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        if (isUnitHasPriority(state, action.getPlayer(), action.getSourceCoordinates()))
            return true;
        result.addMessage(this, "There are other units that need to be moved first.");
        result.invalidate();
        return false;
    }

    private boolean isUnitHasPriority(IGameState state, EPlayer player, Coordinates coords) {
        List<Unit> priorityUnits = state.getPriorityUnits();

        if(priorityUnits.isEmpty())
            return true;

        boolean isNoUnitPlayer = true;
        for (Unit unit : priorityUnits) {
            if (unit.getPlayer() == player && unit.getX() == coords.getX() && unit.getY() == coords.getY())
                return true;
            if (unit.getPlayer() == state.getActualPlayer())
                isNoUnitPlayer = false;
        }
        return isNoUnitPlayer;
    }
}
