package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.gameMaster.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckPlayerMovesLeftTest {
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
        CheckPlayerMovesLeft rule = new CheckPlayerMovesLeft();
        when(iGameState.getActionLeft()).thenReturn(5);
        assertTrue(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getActionLeft()).thenReturn(1);
        assertTrue(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getActionLeft()).thenReturn(0);
        assertFalse(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckPlayerMovesLeft : This player has no action left this turn.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        ruleResult = new RuleResult();
        when(iGameState.getActionLeft()).thenReturn(-5);
        assertFalse(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}