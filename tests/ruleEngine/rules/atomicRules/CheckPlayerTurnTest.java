package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.PrimitiveBoard;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import game.gameMaster.GameState;

import static org.junit.Assert.*;


public class CheckPlayerTurnTest {

    private GameAction gameAction;
    private GameState gameState;
    private PrimitiveBoard board;
    private RuleResult result;

    @Before
    public void setUp(){
        gameAction = Mockito.mock(GameAction.class);
        board = Mockito.mock(PrimitiveBoard.class);
        gameState = Mockito.mock(GameState.class);
    }

    @Test
    public void checkActionValidTurnMocking() {
        CheckPlayerTurn rule = new CheckPlayerTurn();
        Mockito.when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        Mockito.when(gameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        result = new RuleResult();
        String expectedMessage = "";

        assertTrue(rule.checkAction(board, gameState, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidTurnMocking() {
        CheckPlayerTurn rule = new CheckPlayerTurn();
        Mockito.when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        Mockito.when(gameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_SOUTH);
        result = new RuleResult();
        String expectedMessage = "CheckPlayerTurn : This is not player PLAYER_NORTH's turn.\n";

        assertFalse(rule.checkAction(board, gameState, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertFalse(result.isValid());
    }
}
