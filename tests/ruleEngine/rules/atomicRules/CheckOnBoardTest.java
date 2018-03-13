package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckOnBoardTest {
    private IBoard iBoard;
    private IGameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        iBoard = mock(IBoard.class);
        iGameState = mock(IGameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
    }

    @Test
    public void checkActionMocking() {
        CheckOnBoard rule = new CheckOnBoard();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(2, 2));
        when(iBoard.isValidCoordinate(anyInt(), anyInt())).thenReturn(true);
        when(iBoard.isValidCoordinate(99, 99)).thenReturn(false);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(99, 99));
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckOnBoard : Target coordinates are beyond the board's edges.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        ruleResult = new RuleResult();
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(99, 99));
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckOnBoard : Source coordinates are beyond the board's edges.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

    }
}