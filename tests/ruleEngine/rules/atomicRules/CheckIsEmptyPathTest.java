package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.BoardManager;
import game.board.IBoardManager;
import game.board.PrimitiveBoard;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;


public class CheckIsEmptyPathTest {

    private GameAction gameAction;
    private PrimitiveBoard master;
    RuleChecker moveRules;

    //TODO: Tests should be better ...
    @Before
    public void setUp(){
        master = new PrimitiveBoard(25, 20);

        master.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1,0, 1); ///////////////
        master.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1,1, 0); // C  M  -  -
        master.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1,1, 2); // M  -  -  -
                                                                           // -  M  -  -
        master.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER1,0, 0);
        master.setInCommunication(EPlayer.PLAYER1, 0, 0, true);

        gameAction = new GameAction(EPlayer.PLAYER1, EGameActionType.MOVE);

        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        moveRules = RuleChecker.getInstance();
    }

    @Test
    public void isEmptyPath() throws IncorrectGameActionException {
        System.out.println(master.toString());
        System.out.println(moveRules.checkAction(master, gameAction).getLogMessage());
    }

}