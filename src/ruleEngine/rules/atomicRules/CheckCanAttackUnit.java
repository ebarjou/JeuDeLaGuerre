package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameState.IGameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

public class CheckCanAttackUnit implements IRule{
    @Override
    public boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result) {
        Coordinates src = action.getSourceCoordinates();
        boolean canAttack = state.isUnitCanAttack(src);
        if(!canAttack){
            result.invalidate();
            result.addMessage(this, "This unit can't attack this turn.");
            return false;
        }

        return true;
    }
}
