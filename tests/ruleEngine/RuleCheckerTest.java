package ruleEngine;

import game.EPlayer;
import game.board.BoardManager;
import game.board.IBoardManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;

public class RuleCheckerTest {

    private RuleChecker rulechecker;
    private IBoardManager board;

    @Before
    public void setUp(){
        rulechecker = RuleChecker.getInstance();
        board = BoardManager.getInstance();
        board.initBoard(25, 20);
    }

    @Test
    public void checkActionMoveTest() {
        board.addUnit(EUnitData.INFANTRY, EPlayer.PLAYER1,0, 0);
        board.setCommunication(EPlayer.PLAYER1, 0, 0, true);

        GameAction gameAction = new GameAction(EPlayer.PLAYER1, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(1, 1);

        RuleResult result = new RuleResult();
        result.invalidate();
        String expectedMessage = "";

        try {
            result = rulechecker.checkAction(board.getBoard(), gameAction);
        } catch (IncorrectGameActionException e) {
            Assert.assertTrue("Action MOVE unrecognized by RuleChecker.checkAction().", false);
        }
        Assert.assertTrue(result.getLogMessage().equals(expectedMessage));
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidTest() {
        GameAction gameAction = new GameAction(EPlayer.PLAYER1, EGameActionType.NONE);
        try {
            rulechecker.checkAction(board.getBoard(), gameAction);
            Assert.assertTrue("Action NONE should not be recognized by RuleChecker.checkAction().", false);
        } catch (IncorrectGameActionException e) {
            // Intended : Do nothing
        }
    }
}
