package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import game.board.Unit;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckLastMoveTest {
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
        CheckLastMove rule = new CheckLastMove();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        Unit unit = mock(Unit.class);
        when(iGameState.getLastUnitMoved()).thenReturn(unit);
        when(unit.getX()).thenReturn(1);
        when(unit.getY()).thenReturn(1);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(unit.getX()).thenReturn(2);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckLastMove : Unit is not the last one moved.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        ruleResult = new RuleResult();
        when(unit.getX()).thenReturn(1);
        when(unit.getY()).thenReturn(2);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckLastMove : Unit is not the last one moved.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        //Need to test the exception later I guess
    }
}