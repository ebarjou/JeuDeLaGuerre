package ruleEngine.rules.atomicRules;

import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingProperty;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsEmptyAttackPathTest {
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckIsEmptyAttackPath rule;
    private String expectedMessage;

    @Before
    public void setUp() throws Exception {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckIsEmptyAttackPath();
        expectedMessage = "CheckIsEmptyAttackPath : This unit cannot attack here because there is an obstacle.\n";
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(1, 5));
    }

    @Test
    public void checkActionMockingCorrectNormal() {
        when(iGameState.isBuilding(anyInt(), anyInt())).thenReturn(false);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrectPass() {
        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingProperty.PASS);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrectArsenal() {
        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingProperty.ARSENAL);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrectFortress() {
        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingProperty.FORTRESS);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongMountain() {
        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingProperty.MOUNTAIN);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}