package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckIsEmptyAttackPath implements IRule {

    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();

        int dirX = dst.getX() - src.getX();
        int dirY = dst.getY() - src.getY();
        if (dirX != 0) dirX /= Math.abs(dirX); // 1 or -1
        if (dirY != 0) dirY /= Math.abs(dirY); // 1 or -1

        int x = src.getX();
        int y = src.getY();
        while (x != dst.getX() || y != dst.getY()) {
            if ( board.isBuilding(x, y)
                    && !board.getBuildingType(x, y).isAccessible() ) {
                result.addMessage(this,
                        "This unit cannot attack here because there is an obstacle.");
                result.invalidate();
                return false;
            }
            x += dirX;
            y += dirY;
        }
        return true;
    }
}
