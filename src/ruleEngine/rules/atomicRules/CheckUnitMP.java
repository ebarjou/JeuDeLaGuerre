package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckUnitMP implements IRule{


    @Override
    public boolean checkAction(Board board, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();
        int x2 = action.getTargetCoordinates().getX();
        int y2 = action.getTargetCoordinates().getY();

        //TODO: get MP from the unit concerned.
        int MP = 3;
        int dist = board.getDistance(x, y, x2, y2);

        if(dist > MP){
            result.addMessage(this,
                    "Not enough movement point, the unit has "
                            + MP + "MP, and you need " + dist + "MP"
            );
            return false;
        }
        return true;
    }
}
