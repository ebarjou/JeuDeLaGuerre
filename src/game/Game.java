package game;

import game.board.*;
import game.gameMaster.GameMaster;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;
import ruleEngine.entity.EUnitData;
import system.LoadFile;
import ui.GameResponse;
import player.Player;
import ui.UIAction;

import java.io.IOException;

import static ui.commands.GameToUserCall.*;

public class Game {
    private static Game instance = null;
    private Board board;
    private GameMaster gameMaster;
    private Player player1;
    private Player player2;

    public static Game getInstance(){
        return instance;
    }

    public static void init(Player player1, Player player2){
        instance = new Game(player1, player2);
    }

    private Game(Player player1, Player player2) {
        board = new Board(25, 20);
        gameMaster = new GameMaster();
        this.player1 = player1;
        this.player2 = player2;
    }

    public void start(){
        computeCommunication();
        while(true){
            Player player = getPlayer();
            UIAction action = player.getCommand();
            GameResponse response = processCommand(action);
            player.setResponse(response);
        }
    }

    public Player getPlayer() {
        if (gameMaster.getActualState().getActualPlayer() == EPlayer.PLAYER_NORTH)
            return player1;
        return player2;
    }

    public IBoard getBoard(){
        return board;
    }

    public Board getBoardManager(){
        return board;
    }

    public void reinit(Board board, GameMaster gameMaster){
        this.board = board;
        this.gameMaster = gameMaster;
    }

    public GameMaster getGameMaster(){
        return gameMaster;
    }

    private boolean isObstacle(int x, int y, EPlayer player){
        if(!board.isValidCoordinate(x, y))
            return true;
        EUnitData u;
        EBuildingData b;
        try {
            u = board.getUnitType(x, y);
        } catch(NullPointerException e) {
            u = null;
        }
        try {
            b = board.getBuildingType(x, y);
        } catch(NullPointerException e) {
            b = null;
        }
        return (b != null && b == EBuildingData.MOUNTAIN)
                || (u != null && board.getUnitPlayer(x, y) != player && !u.isRelayCommunication());
    }

    private void createCom(int x, int y, EDirection dir, EPlayer player, int rangeMax){
        x += dir.getX();
        y += dir.getY();
        int dist = 1;
        while(!isObstacle(x, y, player) && (rangeMax < 0 || dist <= rangeMax)){
            board.setInCommunication(player, x, y, true);
            EUnitData u;
            try {
                u = board.getUnitType(x, y);
            } catch(NullPointerException e) {
                u = null;
            }
            if(u != null && board.getUnitPlayer(x, y) == player && board.isMarked(x, y)){
                board.setMarked(x, y, true);
                int rangeUnit = 1;
                if(u.isRelayCommunication())
                    rangeUnit = -1;

                for(EDirection d : EDirection.values())
                    createCom(x, y, d, player, rangeUnit);
            }
            board.setMarked(x, y, true);
            x += dir.getX();
            y += dir.getY();
            dist++;
        }
    }

    private void computeCommunication(){
        int w = board.getWidth();
        int h = board.getHeight();

        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                try {
                    EBuildingData building = board.getBuildingType(x, y);
                    if(building == EBuildingData.ARSENAL) {
                        board.setInCommunication(board.getBuildingPlayer(x, y), x, y, true);
                        board.setMarked(x, y, true);
                        for (EDirection direction : EDirection.values())
                            createCom(x, y, direction, board.getBuildingPlayer(x, y), -1);
                    }
                } catch(NullPointerException e) { }
            }
        }
    }

    private GameResponse handleGameAction(UIAction cmd) {
        try {
            GameAction action = cmd.getGameAction(gameMaster.getActualState().getActualPlayer());
            RuleResult res = RuleChecker.getInstance().checkAction(board, action);
            if (res.isValid()) {
                board.moveUnit(action.getSourceCoordinates().getX(),
                        action.getSourceCoordinates().getY(),
                        action.getTargetCoordinates().getX(),
                        action.getTargetCoordinates().getY());
                gameMaster.removeAction();
                //Communication
                board.clearCommunication();
                computeCommunication();

                return new GameResponse(VALID, null, board, gameMaster.getActualState().getActualPlayer());
            } else {
                return new GameResponse(INVALID, res.getLogMessage(), board, gameMaster.getActualState().getActualPlayer());
            }
        } catch (Exception e) {
            return new GameResponse(GAME_ERROR, null, board, gameMaster.getActualState().getActualPlayer());
        }
    }

    public GameResponse processCommand(UIAction cmd) {
        if(cmd == null)  new GameResponse(GAME_ERROR, "Error : null call.", board, gameMaster.getActualState().getActualPlayer());
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                break;
            }
            case LOAD: {
                LoadFile lf = new LoadFile();
                try {
                    lf.loadFile(cmd.getText());
                    board.clearCommunication();
                    computeCommunication();
                } catch (IOException e) {
                    return new GameResponse(INVALID, e.getMessage(), board, gameMaster.getActualState().getActualPlayer());
                }
                return new GameResponse(VALID, null, board, gameMaster.getActualState().getActualPlayer());
            }
            case SAVE: {
                break;
            }
            case REVERT: {
                //board.revert();
                //gameMaster.revert();
                return new GameResponse(VALID, cmd.getErrorMessage(), board, gameMaster.getActualState().getActualPlayer());
            }
            case END_TURN: {
                System.out.println("END TURN");
                //board.clearHistory();
                gameMaster.switchPlayer();
                return new GameResponse(VALID, cmd.getErrorMessage(), board, gameMaster.getActualState().getActualPlayer());
            }
            case CMD_ERROR: {
                return new GameResponse(INVALID, cmd.getErrorMessage(), board, gameMaster.getActualState().getActualPlayer());
            }
            case GAME_ACTION: {
                return handleGameAction(cmd);
            }
            case LIST_UNIT: {
                break;
            }
        }
        return new GameResponse(GAME_ERROR, "Error : Unimplemented call.", board, gameMaster.getActualState().getActualPlayer());
    }
}
