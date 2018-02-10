package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.gameMaster.GameState;
import ruleEngine.items.EUnitData;

public class CheckUnitMP implements IRule{

    private static CheckUnitMP instance = null;

    private CheckUnitMP(){

    }

    public static CheckUnitMP getInstance(){
        if(instance == null)
            instance = new CheckUnitMP();
        return instance;
    }

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();
        int x2 = action.getTargetCoordinates().getX();
        int y2 = action.getTargetCoordinates().getY();

        //TODO: should get the actual Unit's MP left (not the normal value of the Type unit)
        // int MP = 3;
        int MP = EUnitData.getDataFromEUnit(
                board.getUnit(x, y).getId()
        ).getMovementValue();
        int dist = board.getDistance(x, y, x2, y2);

        if(dist > MP){
            result.addMessage(this,
                    "Not enough movement point, the unit has "
                            + MP + " MP, and you need " + dist + " MP"
            );
            return false;
        }
        return true;
    }
}
