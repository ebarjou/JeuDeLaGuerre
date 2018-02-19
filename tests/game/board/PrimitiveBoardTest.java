package game.board;

import game.EPlayer;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;

import static org.junit.Assert.*;

public class PrimitiveBoardTest {
    PrimitiveBoard board;

    @Before
    public void setUp() throws Exception {
        board = new PrimitiveBoard(25, 20);
    }

    @Test
    public void test() throws Exception {
        board.setBuilding(EBuildingData.ARSENAL, EPlayer.PLAYER1, 1, 1);
        board.setUnit(EUnitData.INFANTRY, EPlayer.PLAYER2, 4, 5);
        board.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER1, 1, 1);
        board.setInCommunication(EPlayer.PLAYER1, 2, 4, true);

        assertTrue(board.getUnitType(4, 5)==EUnitData.INFANTRY);
        assertTrue(board.getBuildingType(1, 1)==EBuildingData.ARSENAL);
        assertTrue(board.getUnitType(1, 1)==EUnitData.CAVALRY);

        assertTrue(board.getUnitPlayer(1, 1)==EPlayer.PLAYER1);
        assertTrue(board.getBuildingPlayer(1, 1)==EPlayer.PLAYER1);
        assertTrue(board.getUnitPlayer(4, 5)==EPlayer.PLAYER2);

        try{
            board.getUnitType(5, 5);
            assertTrue(false);
        } catch(NullPointerException e){

        }

        try{
            board.getBuildingPlayer(4, 5);
            assertTrue(false);
        } catch(NullPointerException e){

        }

        try{
            board.getBuildingType(45, 23);
            assertTrue(false);
        } catch(NullPointerException e){

        }

        assertTrue(board.isInCommunication(EPlayer.PLAYER1, 2, 4));
        assertFalse(board.isInCommunication(EPlayer.PLAYER2, 2, 4));
        assertFalse(board.isInCommunication(EPlayer.PLAYER1, 4, 3));

        board.setInCommunication(EPlayer.PLAYER1, 2, 4, false);
        assertFalse(board.isInCommunication(EPlayer.PLAYER1, 2, 4));
        board.setInCommunication(EPlayer.PLAYER1, 5, 13, true);

        assertTrue(board.isInCommunication(EPlayer.PLAYER1, 5, 13));
        board.clearCommunication();
        assertFalse(board.isInCommunication(EPlayer.PLAYER1, 5, 13));

        try{
            board.isInCommunication(EPlayer.PLAYER2, -1, 6);
            assertTrue(false);
        } catch(NullPointerException e){

        }

        PrimitiveBoard boardClone = board.clone();

        assertTrue(board.getUnitType(4, 5)==EUnitData.INFANTRY);
        assertTrue(board.getBuildingType(1, 1)==EBuildingData.ARSENAL);
        assertTrue(board.getUnitType(1, 1)==EUnitData.CAVALRY);

        assertTrue(board.getUnitPlayer(1, 1)==EPlayer.PLAYER1);
        assertTrue(board.getBuildingPlayer(1, 1)==EPlayer.PLAYER1);
        assertTrue(board.getUnitPlayer(4, 5)==EPlayer.PLAYER2);

    }
}