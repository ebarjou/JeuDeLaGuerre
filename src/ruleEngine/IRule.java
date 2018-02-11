package ruleEngine;

import game.board.Board;
import game.gameMaster.GameState;

public interface IRule {
    boolean checkAction(Board board, GameState state, GameAction action, RuleResult result);
}
