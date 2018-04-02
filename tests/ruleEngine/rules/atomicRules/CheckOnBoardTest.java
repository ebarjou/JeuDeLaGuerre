package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckOnBoardTest {
    private IBoard iBoard;
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckOnBoard rule;
    private String expectedMessage;

    @Before
    public void setUp() {
        iBoard = mock(IBoard.class);
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckOnBoard();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(2, 2));
        when(iGameState.isValidCoordinate(anyInt(), anyInt())).thenReturn(true);
        when(iGameState.isValidCoordinate(99, 99)).thenReturn(false);
    }

    @Test
    public void checkActionMockingCorrect() {
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongTarget() {
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(99, 99));
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckOnBoard : Target coordinates are beyond the board's boundaries.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongSource() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(99, 99));
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckOnBoard : Source coordinates are beyond the board's boundaries.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

    }
}