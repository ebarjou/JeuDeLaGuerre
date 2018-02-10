package ruleEngine;

import game.board.Board;
import ruleEngine.gameMaster.GameState;

public interface IRule {
    boolean checkAction(Board board, GameState state, GameAction action, RuleResult result);
}
