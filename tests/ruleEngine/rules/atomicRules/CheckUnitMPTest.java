package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
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
        board = new Board(25, 20);

        // MovementValue = 1
        board.setUnit(EUnitData.INFANTRY, EPlayer.PLAYER_NORTH,0, 0);
        // MovementValue = 2
        board.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH,5, 5);

        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);

        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        rule = new CheckUnitMP();

        gameState = Mockito.mock(GameState.class);
    }

    @Test
    public void checkActionValidMP() {
        gameAction.setSourceCoordinates(5, 5);
        gameAction.setTargetCoordinates(3, 7);

        RuleResult result = new RuleResult();
        String expectedMessage = "";

        assertTrue(rule.checkAction(board, null, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertTrue(result.isValid());
    }

    @Test
    public void checkActionInvalidMPMocking() {
        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        RuleResult result = new RuleResult();
        String expectedMessage = "CheckUnitMP : Not enough movement point, the unit has "
                + "1 MP, and you need 2 MP\n";

        assertFalse(rule.checkAction(board, null, gameAction, result));
        assertTrue(result.getLogMessage().equals(expectedMessage));
        assertFalse(result.isValid());
    }
}
