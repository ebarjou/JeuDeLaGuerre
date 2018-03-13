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

public class CheckIsPriorityUnitTest {
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
        CheckIsPriorityUnit rule = new CheckIsPriorityUnit();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        //when(iGameState.isUnitHasPriority(any(Coordinates.class))).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        //when(iGameState.isUnitHasPriority(any(Coordinates.class))).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsPriorityUnit : There are other units that need to be moved first.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}