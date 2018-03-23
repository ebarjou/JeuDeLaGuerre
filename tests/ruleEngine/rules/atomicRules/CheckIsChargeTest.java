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

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckIsChargeTest {

    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckIsCharge rule;
    private Unit unit;
    private ArrayList<Unit> cantAttackUnits;
    private String expectedMessage;

    @Before
    public void setUp(){
        rule = new CheckIsCharge();
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
    public void checkActionMockingWrongSc1() {
        // Scenario 1
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(5, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(3, 3));
        when(iGameState.getDistance(5, 5, 3, 3)).thenReturn(2);
        expectedMessage = rule.toString() + " : The initiating unit is not able to charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
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
        expectedMessage = rule.toString() + " : The initiating unit is not able to charge.\n";

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
        expectedMessage = rule.toString() + " : The initiating unit is not in a position to proceed a charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}
