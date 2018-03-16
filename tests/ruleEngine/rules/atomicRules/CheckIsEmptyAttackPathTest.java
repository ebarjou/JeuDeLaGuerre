package ruleEngine.rules.atomicRules;

import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsEmptyAttackPathTest {
    private IBoard iBoard;
    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        iBoard = mock(IBoard.class);
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
    }

    @Test
    public void checkActionMocking() {
        CheckIsEmptyAttackPath rule = new CheckIsEmptyAttackPath();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(1, 5));
        when(iGameState.isBuilding(anyInt(), anyInt())).thenReturn(false);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingData.PASS);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingData.ARSENAL);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingData.FORTRESS);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.isBuilding(1, 3)).thenReturn(true);
        when(iGameState.getBuildingType(1, 3)).thenReturn(EBuildingData.MOUNTAIN);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsEmptyAttackPath : This unit cannot attack here because there is an obstacle.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }
}