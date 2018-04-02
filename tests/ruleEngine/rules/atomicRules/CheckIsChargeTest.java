package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Building;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;

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
    private ArrayList<Unit> cantAttackUnits;
    private String expectedMessage;

    @Before
    public void setUp(){
        rule = new CheckIsCharge();
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        when(iGameState.getUnitType(5, 5)).thenReturn(EUnitProperty.INFANTRY);
        when(iGameState.getUnitType(6, 5)).thenReturn(EUnitProperty.CAVALRY);
        when(iGameState.getUnitType(7, 5)).thenReturn(EUnitProperty.CAVALRY);
        when(iGameState.getUnitType(8, 5)).thenReturn(EUnitProperty.CAVALRY);
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
    public void checkActionMockingWrongInfantry() {
        // Scenario 1
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(5, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(4, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not able to charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingCorrectCavalry() {
        // Scenario 2
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));

        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongInfantryLine() {
        // Scenario 3
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(5, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not able to charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongCavalryCantAttack() {
        // Scenario 4
        Unit unit;
        unit = mock(Unit.class);
        when(unit.getX()).thenReturn(8);
        when(unit.getY()).thenReturn(5);
        cantAttackUnits.add(unit);

        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not in a position to proceed a charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongCavalryFar() {
        // Scenario 5
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(10, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not in a position to proceed a charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongCavalryInFortress1() {
        // Scenario 6
        when(iGameState.isBuilding(6, 5)).thenReturn(true);
        when(iGameState.getBuildingType(6, 5)).thenReturn(EBuildingProperty.FORTRESS);

        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not in a position to proceed a charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongCavalryInFortress2() {
        // Scenario 7
        when(iGameState.isBuilding(8, 5)).thenReturn(true);
        when(iGameState.getBuildingType(8, 5)).thenReturn(EBuildingProperty.FORTRESS);

        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not in a position to proceed a charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongEnemyInFortress() {
        // Scenario 8
        when(iGameState.isBuilding(9, 5)).thenReturn(true);
        when(iGameState.getBuildingType(9, 5)).thenReturn(EBuildingProperty.FORTRESS);

        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        expectedMessage = rule.getName() + " : The targeted unit is in a pass or a fortress and cannot be charged.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingCorrectCavalryInPass() {
        // Scenario 9
        when(iGameState.isBuilding(6, 5)).thenReturn(true);
        when(iGameState.getBuildingType(6, 5)).thenReturn(EBuildingProperty.PASS);

        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));

        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongEnemyInPass() {
        // Scenario 10
        when(iGameState.isBuilding(9, 5)).thenReturn(true);
        when(iGameState.getBuildingType(9, 5)).thenReturn(EBuildingProperty.PASS);

        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        expectedMessage = rule.getName() + " : The targeted unit is in a pass or a fortress and cannot be charged.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongCavalryChargeThroughInfantry() {
        // Scenario 11
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(4, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not in a position to proceed a charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongEnemyCavalry() {
        // Scenario 12
        when(iGameState.getUnitPlayer(8, 5)).thenReturn(EPlayer.PLAYER_SOUTH);

        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(6, 5));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(9, 5));
        expectedMessage = rule.getName() + " : The initiating unit is not in a position to proceed a charge.\n";

        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}
