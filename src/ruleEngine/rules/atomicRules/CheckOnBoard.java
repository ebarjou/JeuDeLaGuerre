package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import game.gameMaster.GameState;

public class CheckOnBoard implements IRule {

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        if(board.edge(action.getSourceCoordinates().getX(), action.getTargetCoordinates().getY())){
            result.addMessage(this, "Source coordinates are beyond the board's edges");
            result.invalidate();
            return false;
        }
        if(board.edge(action.getTargetCoordinates().getX(), action.getTargetCoordinates().getY())){
            result.addMessage(this, "Target coordinates are beyond the board's edges");
            result.invalidate();
            return false;
        }
        return true;
    }
}
