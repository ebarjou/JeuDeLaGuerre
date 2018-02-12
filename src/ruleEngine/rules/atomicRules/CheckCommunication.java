package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

public class CheckCommunication implements IRule {

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        GameAction.Coordinates src = action.getSourceCoordinates();
        EUnitData unitData = board.getUnit(src.getX(), src.getY()).getUnit();
        if(unitData.isRelayCommunication() || board.getCommunication(action.getPlayer(), src.getX(), src.getY())) {
            return true;
        }

        result.addMessage(this, "This unit is not in communication, you can't use it");
        result.invalidate();
        return false;
    }
}
