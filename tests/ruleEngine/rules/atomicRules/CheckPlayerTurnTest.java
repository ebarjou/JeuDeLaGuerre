package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import game.gameState.GameState;

import static org.junit.Assert.*;


public class CheckPlayerTurnTest {

    private GameAction gameAction;
    private GameState gameState;
    private Board board;
    private RuleResult result;

    @Before
    public void setUp(){
        gameAction = Mockito.mock(GameAction.class);
        board = Mockito.mock(Board.class);
        gameState = Mockito.mock(GameState.class);
    }

    @Test
    public void checkActionMocking() {
        CheckPlayerTurn rule = new CheckPlayerTurn();
        Mockito.when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        Mockito.when(gameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        result = new RuleResult();
        String expectedMessage = "";

        assertTrue(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertTrue(result.isValid());

        Mockito.when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        Mockito.when(gameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_SOUTH);
        result = new RuleResult();
        expectedMessage = "CheckPlayerTurn : This is not player PLAYER_NORTH's turn.\n";

        assertFalse(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertFalse(result.isValid());
    }
}
