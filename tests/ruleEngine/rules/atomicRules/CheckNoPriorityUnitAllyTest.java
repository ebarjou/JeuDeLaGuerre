package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitProperty;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckNoPriorityUnitAllyTest {
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckNoPriorityUnitAlly rule;
	private String expectedMessage;
	private LinkedList<Unit> units;

	@Before
    public void setUp() {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckNoPriorityUnitAlly();
		expectedMessage = "CheckNoPriorityUnitAlly : There is at least one priority unit.\n";
		units = new LinkedList<>();
		when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
		when(iGameState.getPriorityUnits()).thenReturn(units);
	}

    @Test
    public void checkActionMockingCorrectNoPriorityUnit() {
		assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
		assertTrue(ruleResult.isValid());
	}

	@Test
	public void checkActionMockingCorrectOtherPlayerPriorityUnit() {
		Unit u = new Unit(EUnitProperty.INFANTRY, EPlayer.PLAYER_SOUTH);
		units.add(u);
		assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
		assertTrue(ruleResult.isValid());
	}

	@Test
	public void checkActionMockingWrongPriorityUnit() {
		Unit u = new Unit(EUnitProperty.INFANTRY, EPlayer.PLAYER_NORTH);
        units.add(u);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}