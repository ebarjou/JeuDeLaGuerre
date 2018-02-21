package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckIsUnit implements IRule {

    @Override
    public boolean checkAction(IBoard board, GameState state, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();

        if (board.isUnit(x, y)) {
            if (state.getActualPlayer() != board.getUnitPlayer(x, y)) {
                result.addMessage(this, "This unit is not yours");
                result.invalidate();
                return false;
            }
            return true;
        }
        result.addMessage(this, "There is no unit at (" + x + ";" + y + ")");
        result.invalidate();
        return false;
    }
}
