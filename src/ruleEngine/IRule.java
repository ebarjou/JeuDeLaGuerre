package ruleEngine;

import game.board.IBoard;
import game.gameState.IGameState;

public interface IRule {
    boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result);
}
