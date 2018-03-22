package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;
import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsEnemyUnitTest {
	private GameState iGameState;
	private GameAction gameAction;
	private RuleResult ruleResult;
	private CheckIsEnemyUnit rule;
	private String expectedMessage;

	@Before
	public void setUp() throws Exception {
		iGameState = mock(GameState.class);
		gameAction = mock(GameAction.class);
		ruleResult = new RuleResult();
		rule = new CheckIsEnemyUnit();
		when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(1, 1));
		when(iGameState.isUnit(1, 1)).thenReturn(true);
		when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
	}

	@Test
	public void checkActionMockingCorrect() {
		when(iGameState.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_SOUTH);
		assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
		assertTrue(ruleResult.isValid());
	}

	@Test
	public void checkActionMockingWrongAlly() {
		when(iGameState.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_NORTH);
		assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
		assertFalse(ruleResult.isValid());
		expectedMessage = "CheckIsEnemyUnit : Targeted unit is not an enemy.\n";
		assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

	}
}