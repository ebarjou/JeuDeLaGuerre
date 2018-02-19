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

        assertTrue(board.getUnitType(4, 5)==EUnitData.INFANTRY);
        assertTrue(board.getBuildingType(1, 1)==EBuildingData.ARSENAL);
        assertTrue(board.getUnitType(1, 1)==EUnitData.CAVALRY);

        assertTrue(board.getUnitPlayer(1, 1)==EPlayer.PLAYER1);
        assertTrue(board.getBuildingPlayer(1, 1)==EPlayer.PLAYER1);
        assertTrue(board.getUnitPlayer(4, 5)==EPlayer.PLAYER2);
    }
}