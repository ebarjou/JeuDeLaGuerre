package ruleEngine;

import game.board.Board;

public interface IRule {
    boolean checkAction(Board board, GameAction action, RuleResult result);
}
