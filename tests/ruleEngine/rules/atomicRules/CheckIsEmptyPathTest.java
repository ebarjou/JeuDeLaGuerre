package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.BoardManager;
import game.board.IBoardManager;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;


public class CheckIsEmptyPathTest {

    private GameAction gameAction;
    private IBoardManager master;
    RuleChecker moveRules;

    //TODO: Tests should be better ...
    @Before
    public void setUp(){
        master = new BoardManager();
        master.initBoard(25, 20);

        master.addBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1,0, 1); ///////////////
        master.addBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1,1, 0); // C  M  -  -
        master.addBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER1,1, 2); // M  -  -  -
                                                                           // -  M  -  -
        master.addUnit(EUnitData.CAVALRY, EPlayer.PLAYER1,0, 0);
        master.setCommunication(EPlayer.PLAYER1, 0, 0, true);

        gameAction = new GameAction(EPlayer.PLAYER1, EGameActionType.MOVE);

        gameAction.setSourceCoordinates(0, 0);
        gameAction.setTargetCoordinates(2, 2);

        moveRules = RuleChecker.getInstance();
    }

    @Test
    public void isEmptyPath() throws IncorrectGameActionException {
        System.out.println(master.getBoard().toString());
        System.out.println(moveRules.checkAction(master.getBoard(), gameAction).getLogMessage());
    }

}