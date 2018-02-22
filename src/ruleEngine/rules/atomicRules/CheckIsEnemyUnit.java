package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckIsEnemyUnit implements IRule {

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        int x = action.getTargetCoordinates().getX();
        int y = action.getTargetCoordinates().getY();

        if (board.isUnit(x, y)) {
            if (state.getActualPlayer() == board.getUnitPlayer(x, y)) {
                result.addMessage(this, "This unit is yours, not an enemy's");
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
