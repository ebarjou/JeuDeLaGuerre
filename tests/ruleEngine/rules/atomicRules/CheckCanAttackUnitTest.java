package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitProperty;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class CheckCanAttackUnitTest {

    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckCanAttackUnit rule;
	private LinkedList<Unit> units;
	private Unit testUnit;
	private String expectedMessage;

	@Before
    public void setUp() throws Exception {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckCanAttackUnit();
		units = new LinkedList<>();
		testUnit = new Unit(EUnitProperty.INFANTRY, EPlayer.PLAYER_NORTH);
		units.add(testUnit);
		when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
		when(iGameState.getCantAttackUnits()).thenReturn(units);
		expectedMessage = "CheckCanAttackUnit : This unit can't attack this turn.\n";
	}

    @Test
    public void checkActionMockingCorrect() {
        testUnit.setPosition(2, 2);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrong() {
        testUnit.setPosition(1, 1);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());
    }
}