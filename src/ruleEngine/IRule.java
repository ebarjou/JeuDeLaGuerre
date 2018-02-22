package ruleEngine;

import game.board.IBoard;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;

public interface IRule {
    boolean checkAction(IBoard board, IGameState state, GameAction action, RuleResult result);
}
