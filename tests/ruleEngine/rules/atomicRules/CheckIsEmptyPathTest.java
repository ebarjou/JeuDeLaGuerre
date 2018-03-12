package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import game.board.IBoard;
import game.gameMaster.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;
import static org.junit.Assert.*;

import static org.mockito.Mockito.*;


public class CheckIsEmptyPathTest {
    private IBoard iBoard;
    private IGameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        iBoard = mock(IBoard.class);
        iGameState = mock(IGameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
    }

    @Test
    public void checkActionMocking(){
        CheckIsEmptyPath rule = new CheckIsEmptyPath();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(3, 3));
        when(iBoard.getUnitType(1, 1)).thenReturn(EUnitData.CAVALRY);
        when(iBoard.isValidCoordinate(anyInt(), anyInt())).thenReturn(true);
        when(iBoard.isValidCoordinate(0, 0)).thenReturn(false);
        when(iBoard.isUnit(anyInt(), anyInt())).thenReturn(false);
        when(iBoard.isUnit(0, 2)).thenReturn(true);
        when(iBoard.isBuilding(anyInt(), anyInt())).thenReturn(false);
        when(iBoard.isBuilding(2, 0)).thenReturn(true);
        when(iBoard.isBuilding(2, 1)).thenReturn(true);
        when(iBoard.getBuildingType(2, 0)).thenReturn(EBuildingData.PASS);
        when(iBoard.getBuildingType(2, 1)).thenReturn(EBuildingData.MOUNTAIN);
        assertTrue(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));

        when(iBoard.isValidCoordinate(anyInt(), anyInt())).thenReturn(false);
        assertFalse(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsEmptyPath : There is no path found using 2 movement points.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        ruleResult = new RuleResult();
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(15, 15));
        when(iBoard.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        assertFalse(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckIsEmptyPath : There is no path found using 1 movement points.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));


    }

}