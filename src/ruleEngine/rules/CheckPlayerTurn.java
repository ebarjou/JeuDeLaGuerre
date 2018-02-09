package ruleEngine.rules;

import game.Game;
import game.board.Board;
import game.board.BoardMaster;
import ruleEngine.GameAction;
import ruleEngine.IRule;
import ruleEngine.RuleResult;

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
    public boolean checkAction(Board board, GameAction action, RuleResult result) {
        if (action.getPlayer() != board.getCurrentPlayerTurn()){
            result.invalidate();
            result.addMessage(this, "This is not player " + action.getPlayer().name() + "'s turn."); //TODO: Access player's name through EPlayer enum ?
            return false;
        }

        return true;
    }
}
