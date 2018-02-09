package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.board.entity.EUnit;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.items.EUnitData;

public class CheckCommunication implements IRule {
    private static CheckCommunication instance;

    private CheckCommunication(){

    }

    public static CheckCommunication getInstance(){
        if(instance == null)
            instance = new CheckCommunication();
        return instance;
    }

    @Override
    public boolean checkAction(Board board, GameAction action, RuleResult result) {
        GameAction.Coordinates src = action.getSourceCoordinates();
        EUnit unit = board.getUnit(src.getX(), src.getY()).getId();
        EUnitData unitData = EUnitData.getDataFromEUnit(unit);
        if(unitData.isRelayCommunication() || board.getCommunication(action.getPlayer(), src.getX(), src.getY()))
            return true;
        result.addMessage(this, "This unit is not in communication, you can't use it");
        return false;
    }
}
