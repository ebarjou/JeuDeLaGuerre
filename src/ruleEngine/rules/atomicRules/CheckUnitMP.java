package ruleEngine.rules.atomicRules;

import game.board.Board;
import game.gameMaster.GameState;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckUnitMP implements IRule {

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        int x = action.getSourceCoordinates().getX();
        int y = action.getSourceCoordinates().getY();
        int x2 = action.getTargetCoordinates().getX();
        int y2 = action.getTargetCoordinates().getY();

        int MP = board.getUnit(x, y).getUnit().getMovementValue();
        int dist = board.getDistance(x, y, x2, y2);

        if (dist > MP) {
            result.addMessage(this,
                    "Not enough movement point, the unit has "
                            + MP + " MP, and you need " + dist + " MP");
            result.invalidate();
            return false;
        }
        return true;
    }
}
