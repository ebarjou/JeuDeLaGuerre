package game;

import game.board.*;
import game.gameMaster.GameMaster;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ruleEngine.RuleResult;
import ruleEngine.entity.EBuildingData;
import system.LoadFile;
import ui.GameResponse;
import player.Player;
import ui.UIAction;

import java.io.IOException;

import static ui.commands.GameToUserCall.*;

public class Game {
    private static Game instance = null;
    private IBoardManager boardManager;
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
        boardManager = new BoardManager();
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
        if (gameMaster.getActualState().getActualPlayer() == EPlayer.PLAYER1)
            return player1;
        return player2;
    }

    public Board getBoard(){
        return boardManager.getBoard();
    }

    public IBoardManager getBoardManager(){
        return boardManager;
    }

    public GameMaster getGameMaster(){
        return gameMaster;
    }

    private boolean isObstacle(int x, int y, EPlayer player){
        if(boardManager.getBoard().edge(x, y))
            return true;
        Unit u = boardManager.getBoard().getUnit(x, y);
        Building b = boardManager.getBoard().getBuilding(x, y);
        return (b != null && b.getBuildingData() == EBuildingData.MOUNTAIN)
                || (u != null && u.getPlayer() != player && !u.getUnitData().isRelayCommunication());
    }

    private void createCom(int x, int y, EDirection dir, EPlayer player, VertexCell[][] board, int rangeMax){
        x += dir.getX();
        y += dir.getY();
        int dist = 1;
        while(!isObstacle(x, y, player) && (rangeMax < 0 || dist <= rangeMax)){
            boardManager.setCommunication(player, x, y, true);
            Unit u = board[x][y].c.getUnit();
            if(u != null && u.getPlayer() == player && !board[x][y].isMarked){
                board[x][y].isMarked = true;
                int rangeUnit = 1;
                if(u.getUnitData().isRelayCommunication())
                    rangeUnit = -1;

                for(EDirection d : EDirection.values())
                    createCom(x, y, d, player, board, rangeUnit);
            }
            board[x][y].isMarked = true;
            x += dir.getX();
            y += dir.getY();
            dist++;
        }
    }

    private void computeCommunication(){
        int w = boardManager.getBoard().getWidth();
        int h = boardManager.getBoard().getHeight();
        VertexCell[][] boardVertex = new VertexCell[w][h];
        for(int x = 0; x < w; x++)
            for(int y = 0; y < h; y++)
                boardVertex[x][y] = new VertexCell(boardManager.getBoard().getCell(x, y));

        Board board = boardManager.getBoard();
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                Building building = board.getBuilding(x, y);

                if(building != null && (building.getBuildingData() == EBuildingData.ARSENAL)){
                    boardManager.setCommunication(building.getPlayer(), x, y, true);
                    boardVertex[x][y].isMarked = true;
                    for(EDirection direction : EDirection.values())
                        createCom(x, y, direction, building.getPlayer(), boardVertex, -1);
                }
            }
        }
    }

    private GameResponse handleGameAction(UIAction cmd) {
        try {
            GameAction action = cmd.getGameAction(gameMaster.getActualState().getActualPlayer());
            RuleResult res = RuleChecker.getInstance().checkAction(boardManager.getBoard(), action);
            if (res.isValid()) {
                boardManager.moveUnit(action.getSourceCoordinates().getX(),
                        action.getSourceCoordinates().getY(),
                        action.getTargetCoordinates().getX(),
                        action.getTargetCoordinates().getY());
                gameMaster.removeAction();
                //Communication
                boardManager.clearCommunication();
                computeCommunication();

                return new GameResponse(VALID, null, boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
            } else {
                return new GameResponse(INVALID, res.getLogMessage(), boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
            }
        } catch (Exception e) {
            return new GameResponse(GAME_ERROR, null, boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
        }
    }

    public GameResponse processCommand(UIAction cmd) {
        if(cmd == null)  new GameResponse(GAME_ERROR, "Error : null call.", boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                break;
            }
            case LOAD: {
                LoadFile lf = new LoadFile();
                try {
                    lf.loadFile(cmd.getText());
                    boardManager.clearCommunication();
                    computeCommunication();
                } catch (IOException e) {
                    return new GameResponse(INVALID, e.getMessage(), boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
                }
                return new GameResponse(VALID, null, boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
            }
            case SAVE: {
                break;
            }
            case REVERT: {
                boardManager.revert();
                gameMaster.revert();
                return new GameResponse(VALID, cmd.getErrorMessage(), boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
            }
            case END_TURN: {
                System.out.println("END TURN");
                boardManager.clearHistory();
                gameMaster.switchPlayer();
                return new GameResponse(VALID, cmd.getErrorMessage(), boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
            }
            case CMD_ERROR: {
                return new GameResponse(INVALID, cmd.getErrorMessage(), boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
            }
            case GAME_ACTION: {
                return handleGameAction(cmd);
            }
            case LIST_UNIT: {
                break;
            }
        }
        return new GameResponse(GAME_ERROR, "Error : Unimplemented call.", boardManager.getBoard(), gameMaster.getActualState().getActualPlayer());
    }

    private class VertexCell{
        Cell c;
        boolean isMarked = false;
        VertexCell(Cell c){
            this.c = c;
        }
    }
}
