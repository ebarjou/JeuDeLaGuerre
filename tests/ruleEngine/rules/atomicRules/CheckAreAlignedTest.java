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
    private GameAction gameAction;
    private RuleResult ruleResult;

    @Before
    public void setUp() throws Exception {
        ruleResult = new RuleResult();
        iBoard = mock(IBoard.class);
        iGameState = mock(IGameState.class);
        gameAction = mock(GameAction.class);
    }

    @Test
    public void checkActionMocking() {
        CheckAreAligned rule = new CheckAreAligned();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(1, 8));
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(4, 9));
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String excpectedMessage = "CheckAreAligned : The source is not aligned either horizontally, vertically or diagonally with the target.\n";
        assertTrue(ruleResult.getLogMessage().equals(excpectedMessage));
    }

    @Test
    public void checkActionReal(){
        CheckAreAligned rule = new CheckAreAligned();
        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(1, 1);
        gameAction.setTargetCoordinates(1, 8);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
        gameAction.setSourceCoordinates(1, 1);
        gameAction.setTargetCoordinates(4, 9);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String excpectedMessage = "CheckAreAligned : The source is not aligned either horizontally, vertically or diagonally with the target.\n";
        assertTrue(ruleResult.getLogMessage().equals(excpectedMessage));
        assertFalse(ruleResult.isValid());
    }
}