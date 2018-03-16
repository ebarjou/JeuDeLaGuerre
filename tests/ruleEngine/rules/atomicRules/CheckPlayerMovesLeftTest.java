package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckPlayerMovesLeftTest {
    private IBoard iBoard;
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        iBoard = mock(IBoard.class);
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
    }

    @Test
    public void checkActionMocking() {
        CheckPlayerMovesLeft rule = new CheckPlayerMovesLeft();
        when(iGameState.getActionLeft()).thenReturn(5);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getActionLeft()).thenReturn(1);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getActionLeft()).thenReturn(0);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckPlayerMovesLeft : This player has no action left this turn.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        ruleResult = new RuleResult();
        when(iGameState.getActionLeft()).thenReturn(-5);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}