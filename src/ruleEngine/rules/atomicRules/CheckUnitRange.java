package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.IGameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckUnitRange implements IRule {

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        try {
            int x = action.getSourceCoordinates().getX();
            int y = action.getSourceCoordinates().getY();
            int x2 = action.getTargetCoordinates().getX();
            int y2 = action.getTargetCoordinates().getY();

            int range = board.getUnitType(x, y).getFightRange();
            int dist = board.getDistance(x, y, x2, y2);

            if (dist > range) {
                result.addMessage(this,
                        "Not enough range to attack, the unit has a range of "
                                + range + ", and you need a range of " + dist + ".");
                result.invalidate();
                return false;
            }
            return true;
        } catch (NullPointerException e){
            result.addMessage(this, "Not enough range to attack.");
            result.invalidate();
            return false;
        }
    }
}
