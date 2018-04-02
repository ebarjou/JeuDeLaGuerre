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
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
	private CheckLastMove rule;
	private Unit unit;
	private String expectedMessage;

	@Before
    public void setUp() {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckLastMove();
		when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
		unit = mock(Unit.class);
		when(iGameState.getLastUnitMoved()).thenReturn(unit);
		expectedMessage = "CheckLastMove : Unit is not the last one moved.\n";
    }

    @Test
    public void checkActionMockingCorrect() {
		when(unit.getX()).thenReturn(1);
		when(unit.getY()).thenReturn(1);
		assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
		assertTrue(ruleResult.isValid());
	}

	@Test
	public void checkActionMockingWrong() {
		when(unit.getY()).thenReturn(1);
		when(unit.getX()).thenReturn(2);
		assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
		assertFalse(ruleResult.isValid());
		assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
	}
}