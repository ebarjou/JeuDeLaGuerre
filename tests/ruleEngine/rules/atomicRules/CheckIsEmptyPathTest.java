package ruleEngine.rules.atomicRules;

import game.EPlayer;
import game.board.Board;
import org.junit.Before;
import org.junit.Test;
import ruleEngine.*;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import ruleEngine.exceptions.IncorrectGameActionException;


public class CheckIsEmptyPathTest {

    private GameAction gameAction;
    private Board master;
    RuleChecker moveRules;

    //TODO: Tests should be better ...
    @Before
    public void setUp(){
        master = new Board(25, 20);

        master.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER_NORTH,0, 1); ///////////////
        master.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER_NORTH,1, 0); // C  M  -  -
        master.setBuilding(EBuildingData.MOUNTAIN, EPlayer.PLAYER_NORTH,1, 2); // M  -  -  -
                                                                           // -  M  -  -
        master.setUnit(EUnitData.CAVALRY, EPlayer.PLAYER_NORTH,0, 0);
        master.setInCommunication(EPlayer.PLAYER_NORTH, 0, 0, true);

        gameAction = new GameAction(EPlayer.PLAYER_NORTH, EGameActionType.MOVE);

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