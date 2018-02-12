package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import game.gameMaster.GameState;

public class CheckPlayerTurn implements IRule {

    @Override
    public boolean checkAction(Board board, GameState state, GameAction action, RuleResult result) {
        if (action.getPlayer() != state.getActualPlayer()){
            result.invalidate();
            //TODO: Access player's name through EPlayer enum ?
            result.addMessage(this, "This is not player " + action.getPlayer().name() + "'s turn.");
            return false;
        }

        return true;
    }
}
