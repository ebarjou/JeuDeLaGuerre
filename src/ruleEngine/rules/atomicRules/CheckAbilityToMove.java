package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

public class CheckAbilityToMove implements IRule {

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        try {
            EUnitData unitData = state.getUnitType(src.getX(), src.getY());
            if (unitData.isRelayCommunication() || state.isInCommunication(action.getPlayer(), src.getX(), src.getY())) {
                return true;
            }

        } catch (NullPointerException ignored){
        }

        result.addMessage(this, "This unit is not in communication and cannot be used.");
        result.invalidate();
        return false;
    }
}
