package ruleEngine;

import game.Game;
import game.board.IBoard;
import game.gameMaster.IGameState;
import ruleEngine.exceptions.IncorrectGameActionException;
import ruleEngine.rules.MoveRules;

public class RuleChecker {
    private static RuleChecker instance;

    private IRule moveRuleMaster;

    private RuleChecker() {
        moveRuleMaster = MoveRules.getInstance();
    }

    public static RuleChecker getInstance() {
        if (instance == null)
            instance = new RuleChecker();

        return instance;
    }

    public RuleResult checkAction(IBoard board, IGameState gameState, GameAction action) throws IncorrectGameActionException {
        RuleResult result = new RuleResult();
        switch (action.getActionType()) {
            case MOVE:
                moveRuleMaster.checkAction(board, Game.getInstance().getGameState(), action, result);
                break;
            default:
                throw new IncorrectGameActionException("Unhandled GameAction type.");
        }

        return result;
    }
}
