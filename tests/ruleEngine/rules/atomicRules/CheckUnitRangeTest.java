package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.*;
import ruleEngine.entity.EUnitData;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckUnitRangeTest {

    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckUnitRange rule;
    private Unit unit;
    private ArrayList<Unit> cantAttackUnits;
    private String expectedMessage;

    @Before
    public void setUp(){
        rule = new CheckUnitRange();
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        unit = mock(Unit.class);
        when(iGameState.getUnitType(5, 5)).thenReturn(EUnitData.INFANTRY);
        when(iGameState.getUnitType(6, 5)).thenReturn(EUnitData.CAVALRY);
        when(iGameState.getUnitType(7, 5)).thenReturn(EUnitData.CAVALRY);
        when(iGameState.getUnitType(8, 5)).thenReturn(EUnitData.CAVALRY);
        when(iGameState.isUnit(5, 5)).thenReturn(true);
        when(iGameState.isUnit(6, 5)).thenReturn(true);
        when(iGameState.isUnit(7, 5)).thenReturn(true);
        when(iGameState.isUnit(8, 5)).thenReturn(true);
        when(iGameState.getUnitPlayer(5, 5)).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitPlayer(6, 5)).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitPlayer(7, 5)).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitPlayer(8, 5)).thenReturn(EPlayer.PLAYER_NORTH);
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        cantAttackUnits = new ArrayList<>();
        when(iGameState.getCantAttackUnits()).thenReturn(cantAttackUnits);

    }

    @Test
    public void checkActionMockingCorrectSc1() {
        // Scenario 1
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(5, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(3, 3));
        when(iGameState.getDistance(5, 5, 3, 3)).thenReturn(2);

        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrectSc2() {
        // Scenario 2
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        when(iGameState.getDistance(6, 5, 9, 5)).thenReturn(3);

        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongSc3() {
        // Scenario 3
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(5, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        when(iGameState.getDistance(5, 5, 9, 5)).thenReturn(4);
        expectedMessage = "CheckUnitRange : Not enough range to attack, the unit has a range of 2, and you need a range of 4.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongSc4() {
        // Scenario 4
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        when(iGameState.getDistance(6, 5, 9, 5)).thenReturn(3);
        when(unit.getX()).thenReturn(8);
        when(unit.getY()).thenReturn(5);
        cantAttackUnits.add(unit);
        expectedMessage = "CheckUnitRange : Not enough range to attack, the unit has a range of 2, and you need a range of 3.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongSc5() {
        // Scenario 5
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(5, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(3, 5));
        when(iGameState.getUnitType(5, 5)).thenThrow(new NullPointerException());
        expectedMessage = "CheckUnitRange : Not enough range to attack.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}
