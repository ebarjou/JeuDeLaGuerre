package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsAllyUnitTest {

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
    public void checkActionMocking() {
        CheckIsAllyUnit rule = new CheckIsAllyUnit();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(iBoard.isUnit(1, 1)).thenReturn(true);
        when(iGameState.getActualPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        when(iBoard.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_NORTH);
        assertTrue(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iBoard.getUnitPlayer(1, 1)).thenReturn(EPlayer.PLAYER_SOUTH);
        assertFalse(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        String expectedMessage = "CheckIsAllyUnit : This unit is not owned by PLAYER_NORTH.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

        //TODO: To move on future CheckIsUnitTest
        ruleResult = new RuleResult();
        when(iBoard.isUnit(1, 1)).thenReturn(false);
        assertFalse(rule.checkAction(iBoard, iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        expectedMessage = "CheckIsAllyUnit : There is no unit at (1, 1).\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));

    }
}