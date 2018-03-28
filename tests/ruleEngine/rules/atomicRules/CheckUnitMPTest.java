package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitProperty;

import static org.junit.Assert.*;

public class CheckUnitMPTest {

    private GameAction gameAction;
    private GameState gameState;
    private CheckUnitMP rule;
    private RuleResult result;
    private String expectedMessage;

    @Before
    public void setUp(){

        // MovementValue = 1
        Unit infantry = new Unit(EUnitProperty.INFANTRY, EPlayer.PLAYER_NORTH);
        infantry.setPosition(0, 0);

        // MovementValue = 2
        Unit cavalry = new Unit(EUnitProperty.CAVALRY, EPlayer.PLAYER_NORTH);
        cavalry.setPosition(5, 5);

        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);

        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        rule = new CheckUnitMP();

        gameState = new GameState(25, 20);
        gameState.addUnit(infantry);
        gameState.addUnit(cavalry);
        result = new RuleResult();
        expectedMessage = "CheckUnitMP : Not enough movement point, the unit has 1 MP, and you need 2 MP.\n";
    }

    @Test
    public void checkActionRealCorrect() {
        gameAction.setSourceCoordinates(5, 5);
        gameAction.setTargetCoordinates(7, 7);

        RuleResult result = new RuleResult();

        assertTrue(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.isValid());
    }

    @Test
    public void checkActionRealWrong() {
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        //There is no unit at 0,0 so it should return this message.

        assertFalse(rule.checkAction(gameState, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertFalse(result.isValid());
    }
}
