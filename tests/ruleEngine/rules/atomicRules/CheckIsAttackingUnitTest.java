package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckIsAttackingUnitTest {

	private GameAction gameAction;
	private GameState gameState;
	private CheckIsAttackingUnit rule;
	private RuleResult result;
	private String expectedMessage;

	@Before
	public void setUp() {
		gameState = mock(GameState.class);
		gameAction = mock(GameAction.class);
		rule = new CheckIsAttackingUnit();
		when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(0, 0));
		result = new RuleResult();
		expectedMessage = "CheckIsAttackingUnit : This unit is not suited to attack.\n";
	}

	@Test
	public void checkActionMockingValidInfantry() {
		// INFANTRY
		when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.INFANTRY);
		assertTrue(rule.checkAction(gameState, gameAction, result));
		assertTrue(result.isValid());
	}

	@Test
	public void checkActionMockingValidCavalry() {
		// CAVALRY
		when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.CAVALRY);
		assertTrue(rule.checkAction(gameState, gameAction, result));
		assertTrue(result.isValid());
	}

	@Test
	public void checkActionMockingValidArtillery() {
		// ARTILLERY
		when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.ARTILLERY);
		assertTrue(rule.checkAction(gameState, gameAction, result));
		assertTrue(result.isValid());
	}

	@Test
	public void checkActionMockingValidArtilleryHorse() {
		// ARTILLERY_HORSE
		when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.ARTILLERY_HORSE);
		assertTrue(rule.checkAction(gameState, gameAction, result));
		assertTrue(result.isValid());
	}

	@Test
	public void checkActionMockingInvalidRelay() {
		// RELAY
		when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.RELAY);
		assertFalse(rule.checkAction(gameState, gameAction, result));
		assertFalse(result.isValid());
		assertTrue(result.getLogMessage().contains(expectedMessage));
	}

	@Test
	public void checkActionMockingInvalidRelayHorse() {
		// RELAY_HORSE
		when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.RELAY_HORSE);
		assertFalse(rule.checkAction(gameState, gameAction, result));
		assertFalse(result.isValid());
		assertTrue(result.getLogMessage().contains(expectedMessage));
	}
}
