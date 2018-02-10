package ruleEngine.rules.atomicRules;

import game.board.Board;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;
import ruleEngine.gameMaster.GameState;

public class CheckPlayerTurn implements IRule {

    private static CheckPlayerTurn instance;

    private CheckPlayerTurn(){
    }

    public static CheckPlayerTurn getInstance(){
        if (instance == null)
            instance = new CheckPlayerTurn();

        return instance;
    }

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
