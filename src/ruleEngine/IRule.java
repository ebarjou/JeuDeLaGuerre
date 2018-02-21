package ruleEngine;

import game.board.Board;
import game.board.IBoard;
import game.gameMaster.GameState;

public interface IRule {
    boolean checkAction(IBoard board, GameState state, GameAction action, RuleResult result);
}
