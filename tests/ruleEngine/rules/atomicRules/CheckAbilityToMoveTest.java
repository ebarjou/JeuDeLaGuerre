package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import game.gameState.IGameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckAbilityToMoveTest {

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
        CheckAbilityToMove rule = new CheckAbilityToMove();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY_HORSE);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY_HORSE);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());

        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        String expectedMessage = "CheckAbilityToMove : This unit is not in communication and cannot be used.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());

        //TODO : Wrong behavior
        ruleResult = new RuleResult();
        when(iGameState.getUnitType(1, 1)).thenThrow(new NullPointerException());
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        expectedMessage = "CheckAbilityToMove : This unit is not in communication and cannot be used.\n";
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());

    }
}