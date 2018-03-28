package game.board;

import game.EPlayer;
import game.board.exceptions.IllegalBoardCallException;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.entity.EBuildingProperty;
import ruleEngine.entity.EUnitProperty;

import static org.junit.Assert.*;

public class BoardTest {
    Board board;

    @Before
    public void setUp() throws Exception {
        board = new Board(25, 20);
    }

    @Test
    public void test() throws Exception {
        board.setBuilding(EBuildingProperty.ARSENAL, EPlayer.PLAYER_NORTH, 1, 1);
        board.setUnit(EUnitProperty.INFANTRY, EPlayer.PLAYER_SOUTH, 4, 5);
        board.setUnit(EUnitProperty.CAVALRY, EPlayer.PLAYER_NORTH, 1, 1);
        board.setInCommunication(EPlayer.PLAYER_NORTH, 2, 4, true);

        assertTrue(board.getUnitType(4, 5) == EUnitProperty.INFANTRY);
        assertTrue(board.getBuildingType(1, 1) == EBuildingProperty.ARSENAL);
        assertTrue(board.getUnitType(1, 1) == EUnitProperty.CAVALRY);

        assertTrue(board.getUnitPlayer(1, 1) == EPlayer.PLAYER_NORTH);
        assertTrue(board.getBuildingPlayer(1, 1) == EPlayer.PLAYER_NORTH);
        assertTrue(board.getUnitPlayer(4, 5) == EPlayer.PLAYER_SOUTH);

        try{
            board.getUnitType(5, 5);
            assertTrue(false);
        } catch(IllegalBoardCallException e){

        }

        try{
            board.getBuildingPlayer(4, 5);
            assertTrue(false);
        } catch(IllegalBoardCallException e){

        }

        try{
            board.getBuildingType(45, 23);
            assertTrue(false);
        } catch(IllegalBoardCallException e){

        }

        assertTrue(board.isInCommunication(EPlayer.PLAYER_NORTH, 2, 4));
        assertFalse(board.isInCommunication(EPlayer.PLAYER_SOUTH, 2, 4));
        assertFalse(board.isInCommunication(EPlayer.PLAYER_NORTH, 4, 3));

        board.setInCommunication(EPlayer.PLAYER_NORTH, 2, 4, false);
        assertFalse(board.isInCommunication(EPlayer.PLAYER_NORTH, 2, 4));
        board.setInCommunication(EPlayer.PLAYER_NORTH, 5, 13, true);

        assertTrue(board.isInCommunication(EPlayer.PLAYER_NORTH, 5, 13));
        board.clearCommunication();
        assertFalse(board.isInCommunication(EPlayer.PLAYER_NORTH, 5, 13));

        try{
            board.isInCommunication(EPlayer.PLAYER_SOUTH, -1, 6);
            assertTrue(false);
        } catch(IllegalBoardCallException e){

        }

        Board boardClone = board.clone();

        assertTrue(board.getUnitType(4, 5) == EUnitProperty.INFANTRY);
        assertTrue(board.getBuildingType(1, 1) == EBuildingProperty.ARSENAL);
        assertTrue(board.getUnitType(1, 1) == EUnitProperty.CAVALRY);

        assertTrue(board.getUnitPlayer(1, 1) == EPlayer.PLAYER_NORTH);
        assertTrue(board.getBuildingPlayer(1, 1) == EPlayer.PLAYER_NORTH);
        assertTrue(board.getUnitPlayer(4, 5) == EPlayer.PLAYER_SOUTH);

    }
}