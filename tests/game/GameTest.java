package game;

import org.junit.Before;
import org.junit.Test;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ui.CommandException;
import ui.SharedCommand;
import ui.TermUI;
import ui.commands.UserToGameCall;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    @Before
    public void setUp() throws Exception {
        game = new Game(new TermUI());
    }

    private GameAction convert(SharedCommand cmd){
        try {
            Method method = Game.class.getDeclaredMethod("convert", SharedCommand.class);
            method.setAccessible(true);
            return (GameAction) method.invoke(game, cmd);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Test
    public void convertTest() throws CommandException {
        SharedCommand cmd = mock(SharedCommand.class);
        when(cmd.getCommand()).thenReturn(UserToGameCall.MOVE);
        when(cmd.getCoords1()).thenReturn(new int[]{4, 6});
        when(cmd.getCoords2()).thenReturn(new int[]{8, 9});
        GameAction result = convert(cmd);

        assertTrue(result.getActionType().equals(EGameActionType.MOVE));
        assertTrue(result.getSourceCoordinates().getX() == 4);
        assertTrue(result.getSourceCoordinates().getY() == 6);
        assertTrue(result.getTargetCoordinates().getX() == 8);
        assertTrue(result.getTargetCoordinates().getY() == 9);

        when(cmd.getCommand()).thenReturn(UserToGameCall.ATTACK);
        when(cmd.getCoords1()).thenReturn(new int[]{8, 14});
        when(cmd.getCoords2()).thenReturn(new int[]{0, 1});
        result = convert(cmd);

        assertTrue(result.getActionType().equals(EGameActionType.ATTACK));
        assertTrue(result.getSourceCoordinates().getX() == 8);
        assertTrue(result.getSourceCoordinates().getY() == 14);
        assertTrue(result.getTargetCoordinates().getX() == 0);
        assertTrue(result.getTargetCoordinates().getY() == 1);

        when(cmd.getCommand()).thenReturn(UserToGameCall.EXIT);
        when(cmd.getCoords1()).thenReturn(new int[]{1, 2});
        when(cmd.getCoords2()).thenReturn(new int[]{3, 4});
        result = convert(cmd);

        assertTrue(result == null);
    }
}