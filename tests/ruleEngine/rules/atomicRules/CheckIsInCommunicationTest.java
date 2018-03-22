package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.IBoard;
import org.junit.Before;
import org.junit.Test;import game.gameState.GameState;
import ruleEngine.Coordinates;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CheckIsInCommunicationTest {

    private GameState iGameState;
    private GameAction gameAction;
    private RuleResult ruleResult;
    private CheckIsInCommunication rule;
    private String expectedMessage;

    @Before
    public void setUp() throws Exception {
        iGameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        ruleResult = new RuleResult();
        rule = new CheckIsInCommunication();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(1, 1));
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER_NORTH);
        expectedMessage = "CheckIsInCommunication : This unit is not in the player communication.\n";
    }

    @Test
    public void checkActionMockingCorrectComm() {
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
    }

    @Test
    public void checkActionMockingCorrectRelayAndComm() {
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingCorrectRelayHorseAndComm() {
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY_HORSE);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(true);
        assertTrue(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.isValid());
    }

    @Test
    public void checkActionMockingWrongRelayOnly() {
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongRelayHorseOnly() {
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.RELAY_HORSE);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertFalse(ruleResult.isValid());
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionMockingWrongNoComm() {
        when(iGameState.getUnitType(1, 1)).thenReturn(EUnitData.INFANTRY);
        when(iGameState.isInCommunication(EPlayer.PLAYER_NORTH, 1, 1)).thenReturn(false);
        assertFalse(rule.checkAction(iGameState, gameAction, ruleResult));
        assertTrue(ruleResult.getLogMessage().equals(expectedMessage));
        assertFalse(ruleResult.isValid());
    }
}