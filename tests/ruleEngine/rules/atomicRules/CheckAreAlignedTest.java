package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class CheckAreAlignedTest {

    private IBoard iBoard;
    private IGameState iGameState;
    private GameAction action;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        ruleResult = new RuleResult();
        iBoard = mock(IBoard.class);
        iGameState = mock(IGameState.class);
        action = mock(GameAction.class);
    }

    @Test
    public void checkActionMocking() {
        CheckAreAligned rule = new CheckAreAligned();
        when(action.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(action.getTargetCoordinates()).thenReturn(new Coordinates(1, 8));
        assertTrue(rule.checkAction(iBoard, iGameState, action, ruleResult));
        when(action.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(action.getTargetCoordinates()).thenReturn(new Coordinates(4, 9));
        assertFalse(rule.checkAction(iBoard, iGameState, action, ruleResult));
        String excpectedMessage = "CheckAreAligned : The source is not aligned either horizontally, vertically or diagonally with the target.\n";
        assertTrue(ruleResult.getLogMessage().equals(excpectedMessage));
    }

    @Test
    public void checkActionReal(){
        CheckAreAligned rule = new CheckAreAligned();
        action = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        action.setSourceCoordinates(1, 1);
        action.setTargetCoordinates(1, 8);
        assertTrue(rule.checkAction(iBoard, iGameState, action, ruleResult));
        assertTrue(ruleResult.isValid());
        action.setSourceCoordinates(1, 1);
        action.setTargetCoordinates(4, 9);
        assertFalse(rule.checkAction(iBoard, iGameState, action, ruleResult));
        String excpectedMessage = "CheckAreAligned : The source is not aligned either horizontally, vertically or diagonally with the target.\n";
        assertTrue(ruleResult.getLogMessage().equals(excpectedMessage));
        assertFalse(ruleResult.isValid());
    }
}