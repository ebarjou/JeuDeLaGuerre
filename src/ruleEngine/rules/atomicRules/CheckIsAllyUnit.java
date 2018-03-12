package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckIsAllyUnit implements IRule {

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();

        if (board.isUnit(x, y)) {
            if (state.getActualPlayer() != board.getUnitPlayer(x, y)) {
                result.addMessage(this, "This unit is not owned by " + state.getActualPlayer().name() + ".");
                result.invalidate();
                return false;
            }

            return true;
        }
        //TODO: Code repetition here, use rule dependencies instead (-> CheckIsEnemyUnit)
        result.addMessage(this, "There is no unit at (" + x + ", " + y + ").");
        result.invalidate();
        return false;
    }
}
