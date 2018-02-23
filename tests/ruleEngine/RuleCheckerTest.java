package ruleEngine;

import game.EPlayer;
import game.board.Board;
import game.board.Unit;
import game.gameMaster.GameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;

import static org.junit.Assert.assertTrue;

public class RuleCheckerTest {

    private RuleChecker rulechecker;
    private GameState gameState;

    @Before
    public void setUp(){
        rulechecker = RuleChecker.getInstance();
        gameState = new GameState(25, 20);
    }

    @Test
    public void checkActionMoveTest() {
        Unit unit = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        unit.setPosition(0, 0);
        gameState.addUnit(unit);

        Board board = gameState.getBoardManager();
        board.setInCommunication(EPlayer.PLAYER_NORTH, 0, 0, true);

        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(1, 1);

        RuleResult result = new RuleResult();
        result.invalidate();
        String expectedMessage = "";

        try {
            result = rulechecker.checkAction(board, gameState, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action MOVE unrecognized by RuleChecker.checkAction().", false);
        }
        System.out.println(result.getLogMessage());
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidTest() {
        Board board = gameState.getBoardManager();
        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.NONE);
        try {
            rulechecker.checkAction(board, gameState, gameAction);
            assertTrue("Action NONE should not be recognized by RuleChecker.checkAction().", false);
        } catch (IncorrectGameActionException e) {
            // Intended : Do nothing
        }
    }
}
