package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.Coordinates;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckIsAttackingUnitTest {

    private GameAction gameAction;
    private GameState gameState;
    private CheckIsAttackingUnit rule;

    @Before
    public void setUp(){
        gameState = mock(GameState.class);
        gameAction = mock(GameAction.class);
        rule = new CheckIsAttackingUnit();
        when(gameAction.getSourceCoordinates()).thenReturn(new Coordinates(0, 0));
    }

    @Test
    public void checkActionValidAttackingMocking() {
        // INFANTRY
        when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.INFANTRY);

        RuleResult result = new RuleResult();
        String expectedMessage = "";

        assertTrue(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));

        // CAVALRY
        when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.CAVALRY);
        result = new RuleResult(); expectedMessage = "";

        assertTrue(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));

        // ARTILLERY
        when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.ARTILLERY);
        result = new RuleResult(); expectedMessage = "";

        assertTrue(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));

        // ARTILLERY_HORSE
        when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.ARTILLERY_HORSE);
        result = new RuleResult(); expectedMessage = "";

        assertTrue(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.isValid());
        assertTrue(result.getLogMessage().equals(expectedMessage));
    }

    @Test
    public void checkActionInvalidAttackingMocking() {
        // RELAY
        when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.RELAY);

        RuleResult result = new RuleResult();
        String expectedMessage = "CheckIsAttackingUnit : This unit is not suited to attack.\n";

        assertFalse(rule.checkAction(gameState, gameAction, result));
        assertFalse(result.isValid());
        assertTrue(result.getLogMessage().contains(expectedMessage));

        // RELAY_HORSE
        when(gameState.getUnitType(0, 0)).thenReturn(EUnitData.RELAY_HORSE);

        result = new RuleResult(); expectedMessage = "CheckIsAttackingUnit : This unit is not suited to attack.\n";

        assertFalse(rule.checkAction(gameState, gameAction, result));
        assertFalse(result.isValid());
        assertTrue(result.getLogMessage().contains(expectedMessage));
    }
}
