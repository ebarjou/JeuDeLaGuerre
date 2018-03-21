package ruleEngine.rules.atomicRules;

import game.board.exceptions.IllegalBoardCallException;
import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.rules.newRules.IRule;

public class CheckUnitMP implements IRule {

    @Override
    public boolean checkAction(GameState state, GameAction action, RuleResult result) {
        try {
            int x = action.getSourceCoordinates().getX();
            int y = action.getSourceCoordinates().getY();
            int x2 = action.getTargetCoordinates().getX();
            int y2 = action.getTargetCoordinates().getY();

            int MP = state.getUnitType(x, y).getMovementValue();
            int dist = state.getDistance(x, y, x2, y2);

            if (dist > MP) {
                result.addMessage(this,
                        "Not enough movement point, the unit has "
                                + MP + " MP, and you need " + dist + " MP");
                result.invalidate();
                return false;
            }
            return true;
        } catch (IllegalBoardCallException e){
            result.addMessage(this, "Not enough movement point");
            result.invalidate();
            return false;
        }
    }


    public String toString(){
        return this.getClass().getSimpleName();
    }
}
