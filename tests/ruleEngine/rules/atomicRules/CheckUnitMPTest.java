package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import game.board.Unit;
import game.gameState.GameState;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleResult;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.*;

public class CheckUnitMPTest {

    private GameAction gameAction;
    private GameState gameState;
    private Board board;
    private CheckUnitMP rule;

    @Before
    public void setUp(){
        //board = new Board(25, 20);

        // MovementValue = 1
        Unit infantry = new Unit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH);
        infantry.setPosition(0, 0);
        //board.setUnit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH,0, 0);
        // MovementValue = 2
        Unit cavalry = new Unit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH);
        cavalry.setPosition(5, 5);
        //board.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH,5, 5);*/


        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);

        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        rule = new CheckUnitMP();

        gameState = new GameState(25, 20);
        gameState.addUnit(infantry);
        gameState.addUnit(cavalry);
    }

    @Test
    public void checkActionValidMP() {
        gameAction.setSourceCoordinates(5, 5);
        gameAction.setTargetCoordinates(7, 7);

        RuleResult result = new RuleResult();
        String expectedMessage = "";

        boolean valid = rule.checkAction(gameState, gameAction, result);

        assertTrue(valid);
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidMPMocking() {
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        RuleResult result = new RuleResult();
        //There is no unit at 0,0 so it should return this message.
        String expectedMessage = "CheckUnitMP : Not enough movement point, the unit has 1 MP, and you need 2 MP";


        assertFalse(rule.checkAction(gameState, gameAction, result));

        System.out.println(result.getLogMessage());
        //System.out.println(gameState.toString());

        assertTrue(result.getLogMessage().contains(expectedMessage));
        assertFalse(result.isValid());
    }
}
