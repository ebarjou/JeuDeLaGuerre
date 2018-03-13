package ruleEngine;

import game.board.IBoard;
import game.gameState.IGameState;

public abstract class Rule {
    public abstract boolean checkAction(IGameState state, GameAction action, RuleResult result);

    @Override
    public boolean equals(Object obj) {
        return this.getClass().getName().equals(obj.getClass().getName());
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }
}
