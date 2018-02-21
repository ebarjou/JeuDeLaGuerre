package ruleEngine;

import game.EPlayer;
import game.board.Board;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;

import static org.junit.Assert.assertTrue;

public class RuleCheckerTest {

    private RuleChecker rulechecker;
    private Board board;

    @Before
    public void setUp(){
        rulechecker = RuleChecker.getInstance();
        board = new Board(25, 20);
    }

    @Test
    public void checkActionMoveTest() {
        board.setUnit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH,0, 0);
        board.setInCommunication(EPlayer.PLAYER_NORTH, 0, 0, true);

        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(1, 1);

        RuleResult result = new RuleResult();
        result.invalidate();
        String expectedMessage = "";

        try {
            result = rulechecker.checkAction(board, gameAction);
        } catch (IncorrectGameActionException e) {
            assertTrue("Action MOVE unrecognized by RuleChecker.checkAction().", false);
        }
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidTest() {
        GameAction gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.NONE);
        try {
            rulechecker.checkAction(board, gameAction);
            assertTrue("Action NONE should not be recognized by RuleChecker.checkAction().", false);
        } catch (IncorrectGameActionException e) {
            // Intended : Do nothing
        }
    }
}
