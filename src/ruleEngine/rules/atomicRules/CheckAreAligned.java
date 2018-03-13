package ruleEngine.rules.atomicRules;

import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.Rule;
import ruleEngine.RuleResult;

public class CheckAreAligned extends Rule {

    @Override
    public boolean checkAction(IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        Coordinates dst = action.getTargetCoordinates();

        int diffX = Math.abs(src.getX() - dst.getX());
        int diffY = Math.abs(src.getY() - dst.getY());

        if ( ( diffX != diffY ) && (diffX != 0) && (diffY != 0) ) {
            result.addMessage(this,
                    "The source is not aligned either horizontally, vertically or diagonally with the target.");
            result.invalidate();
            return false;
        }
        return true;
    }
}
