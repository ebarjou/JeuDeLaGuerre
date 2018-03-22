package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsPriorityUnitTest {
    private GameState gameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private String expectedMessage;
    private CheckIsPriorityUnit rule;
    private LinkedList<Unit> units;

    @Before
    public void setUp() {
        gameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        expectedMessage = "CheckIsPriorityUnit : There are other units that need to be moved first.\n";
        rule = new CheckIsPriorityUnit();
        units = new LinkedList<>();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_SOUTH);
        when(gameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_SOUTH);
        when(gameState.getPriorityUnits()).thenReturn(units);
    }

    @Test
    public void checkActionMockingCorrectNoPriorityUnits() {
        assertTrue(rule.checkAction(gameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrectPriorityUnitSelected() {
        Unit u = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_SOUTH);
        u.setPosition(1, 1);
        units.add(u);
        assertTrue(rule.checkAction(gameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrectWrongPriorityUnitNotSelected() {
        Unit u = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_SOUTH);
        u.setPosition(0, 0);
        units.add(u);
        assertFalse(rule.checkAction(gameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}