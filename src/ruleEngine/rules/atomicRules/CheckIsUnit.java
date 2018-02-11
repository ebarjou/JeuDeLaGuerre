package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import game.gameMaster.GameState;

public class CheckIsUnit implements IRule{

    private static CheckIsUnit instance;
    private CheckIsUnit(){
    }

    public static CheckIsUnit getInstance(){
        if(instance == null)
            instance = new CheckIsUnit();
        return instance;
    }

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();

        if(board.getUnit(x, y) != null)
            return true;

        result.addMessage(this, "There is no unit at (" + x + ";" + y + ")");
        return false;
    }
}
