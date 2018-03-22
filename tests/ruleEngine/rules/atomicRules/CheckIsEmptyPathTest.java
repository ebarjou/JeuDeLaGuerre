package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;


public class CheckIsEmptyPathTest {
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckIsEmptyPath rule;
    private String expectedMessage;

    @Before
    public void setUp() throws Exception {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckIsEmptyPath();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(iGameState.isUnit(anyInt(), anyInt())).thenReturn(false);
        when(iGameState.isUnit(0, 2)).thenReturn(true);
        when(iGameState.isBuilding(anyInt(), anyInt())).thenReturn(false);
        when(iGameState.isBuilding(2, 0)).thenReturn(true);
        when(iGameState.isBuilding(2, 1)).thenReturn(true);
        when(iGameState.getBuildingType(2, 0)).thenReturn(EBuildingData.PASS);
        when(iGameState.getBuildingType(2, 1)).thenReturn(EBuildingData.MOUNTAIN);
        when(iGameState.isValidCoordinate(0, 0)).thenReturn(false);
    }

    @Test
    public void checkActionMockingCorrect(){
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(3, 3));
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.CAVALRY);
        when(iGameState.isValidCoordinate(anyInt(), anyInt())).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongCoordinates(){
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(3, 3));
        when(iGameState.isValidCoordinate(anyInt(), anyInt())).thenReturn(false);
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.CAVALRY);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongRange(){
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(15, 15));
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckIsEmptyPath : There is no path found using 1 movement points.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

}