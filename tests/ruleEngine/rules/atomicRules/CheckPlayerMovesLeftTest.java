package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckPlayerMovesLeftTest {
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckPlayerMovesLeft rule;
    private String expectedMessage;

    @Before
    public void setUp() throws Exception {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckPlayerMovesLeft();
        expectedMessage = "CheckPlayerMovesLeft : This player has no action left this turn.\n";
    }

    @Test
    public void checkActionMockingCorrect5M() {
        when(iGameState.getActionLeft()).thenReturn(5);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrect1M() {
        when(iGameState.getActionLeft()).thenReturn(1);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrong0M() {
        when(iGameState.getActionLeft()).thenReturn(0);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongNegativeValueM() {
        when(iGameState.getActionLeft()).thenReturn(-5);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}