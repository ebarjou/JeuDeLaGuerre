package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

public class CheckCommunication implements IRule {

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        try {
            EUnitData unitData = board.getUnitType(src.getX(), src.getY());
            if (unitData.isRelayCommunication() || board.isInCommunication(action.getPlayer(), src.getX(), src.getY())) {
                return true;
            }

            result.addMessage(this, "This unit is not in communication, you can't use it");
            result.invalidate();
            return false;
        } catch (NullPointerException e){
            result.addMessage(this, "This unit is not in communication, you can't use it");
            result.invalidate();
            return false;
        }
    }
}
