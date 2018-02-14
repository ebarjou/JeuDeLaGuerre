package ruleEngine.rules;

import game.board.Board;
import org.junit.Before;
import org.mockito.Mockito;

import ruleEngine.GameAction;

public class MoveRulesTest {

    private GameAction gameAction;
    private Board board;

    //TODO: Create new tests

    @Before
    public void setUp() {
        gameAction = Mockito.mock(GameAction.class);
        board = Mockito.mock(Board.class);
    }

/*    @Test
    public void checkActionCorrectMoveMock() {
        when(board.getCurrentPlayerTurn()).thenReturn(EPlayer.PLAYER1);
        when(gameAction.getPlayer()).thenReturn(EPlayer.PLAYER1);
        when(gameAction.getActionType()).thenReturn(EGameActionType.MOVE);
        when(gameAction.getSourceCoordinates().getX()).thenReturn(2);
        when(gameAction.getSourceCoordinates().getY()).thenReturn(2);
        when(gameAction.getTargetCoordinates().getX()).thenReturn(3);
        when(gameAction.getTargetCoordinates().getY()).thenReturn(3);
        //Problem when trying to get Unit from a case (seems package-private)
        //Also maybe mocks are overkill for masterrules

        //...
    }*/
}