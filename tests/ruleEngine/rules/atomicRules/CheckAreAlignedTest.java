package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class CheckAreAlignedTest {

    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckAreAligned rule;

    @Before
    public void setUp() throws Exception {
        ruleResult = new RuleResult();
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        rule = new CheckAreAligned();
    }

    @Test
    public void checkActionMockingCorrect() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(1, 8));
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
    }

    @Test
    public void checkActionMockingError() {
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getTargetCoordinates()).thenReturn(new Coordinates(4, 9));
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String excpectedMessage = "CheckAreAligned : The source is not aligned either horizontally, vertically or diagonally with the target.\n";
        assertTrue(ruleResult.getLogMessage().equals(excpectedMessage));
    }

    @Test
    public void checkActionRealCorrect(){
		gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(1, 1);
		gameAction.setTargetCoordinates(1, 8);
		assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
		assertTrue(ruleResult.isValid());
	}

    @Test
    public void checkActionRealWrong(){
		gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);
        gameAction.setSourceCoordinates(1, 1);
        gameAction.setTargetCoordinates(4, 9);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String expectedMessage = "CheckAreAligned : The source is not aligned either horizontally, vertically or diagonally with the target.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());
    }
}