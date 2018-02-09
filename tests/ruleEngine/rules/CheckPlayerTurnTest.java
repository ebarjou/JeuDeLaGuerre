package ruleEngine.rules;

import game.EPlayer;
import game.Game;
import game.board.Board;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;

public class CheckPlayerTurnTest {

    private GameAction gameAction;
    private Board board;
    private RuleResult result;

    @Before
    public void setUp(){
        gameAction = Mockito.mock(GameAction.class);
        board = Mockito.mock(Board.class);
    }

    @Test
    public void checkActionValidTurnMocking() {
        Mockito.when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER1);
        Mockito.when(board.getCurrentPlayerTurn()).thenReturn(EPlayer.PLAYER1);
        result = new RuleResult();
        String expectedMessage = "";

        Assert.assertTrue(CheckPlayerTurn.getInstance().checkAction(board, gameAction, result));
        Assert.assertTrue(result.getLogMessage().equals(expectedMessage));
        Assert.assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidTurnMocking() {
        Mockito.when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER1);
        Mockito.when(board.getCurrentPlayerTurn()).thenReturn(EPlayer.PLAYER2);
        result = new RuleResult();
        String expectedMessage = "CheckPlayerTurn : This is not player PLAYER1's turn.\n";

        Assert.assertFalse(CheckPlayerTurn.getInstance().checkAction(board, gameAction, result));
        Assert.assertTrue(result.getLogMessage().equals(expectedMessage));
        Assert.assertFalse(result.isValid());
    }
}