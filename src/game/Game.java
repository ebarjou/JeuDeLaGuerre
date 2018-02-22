package game;

import game.board.*;
import game.gameMaster.GameState;
import game.gameMaster.IGameState;
import ruleEngine.Coordinates;
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
import java.util.Stack;

import static ui.commands.GameToUserCall.*;

public class Game {
    private static Game instance = null;
    //private Board board;
    //private GameMaster gameMaster;
    private GameState gameState;
    private Stack<GameState> historyGameState;
    private Player player1;
    private Player player2;

    public static Game getInstance(){
        return instance;
    }

    public static void init(Player player1, Player player2){
        instance = new Game(player1, player2);
    }

    private Game(Player player1, Player player2) {
        gameState = new GameState(25, 20); // Not sure if we should let 25 / 20 like this
        this.player1 = player1;
        this.player2 = player2;
        historyGameState = new Stack<>();
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
        if (gameState.getActualPlayer() == EPlayer.PLAYER_NORTH)
            return player1;
        return player2;
    }

    public IBoard getBoard(){
        return gameState.getBoard();
    }

    public Board getBoardManager(){
        return gameState.getBoard();
    }

    public IGameState getGameState(){
        return gameState;
    }

    public GameState getGameStateManager() {
        return gameState;
    }

    public void reinit(GameState gameState){
        this.gameState = gameState;
        computeCommunication();
    }

    private boolean isObstacle(int x, int y, EPlayer player){
        Board board = gameState.getBoard();
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
            gameState.getBoard().setInCommunication(player, x, y, true);
            EUnitData u;
            try {
                u = gameState.getBoard().getUnitType(x, y);
            } catch(NullPointerException e) {
                u = null;
            }
            if(u != null && gameState.getBoard().getUnitPlayer(x, y) == player && !gameState.getBoard().isMarked(x, y)){
                gameState.getBoard().setMarked(x, y, true);
                int rangeUnit = 1;
                if(u.isRelayCommunication())
                    rangeUnit = -1;

                for(EDirection d : EDirection.values())
                    createCom(x, y, d, player, rangeUnit);
            }
            //board.setMarked(x, y, true);
            x += dir.getX();
            y += dir.getY();
            dist++;
        }
    }

    private void computeCommunication(){
        Board board = gameState.getBoard();
        int w = board.getWidth();
        int h = board.getHeight();

        board.clearCommunication();
        board.clearMarked();
        System.out.println(board.toString());

        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                EBuildingData building;
                try {
                    building = board.getBuildingType(x, y);
                } catch(NullPointerException e) {
                    building = null;
                }

                if(building == EBuildingData.ARSENAL) {
                    board.setInCommunication(board.getBuildingPlayer(x, y), x, y, true);
                    board.setMarked(x, y, true);
                    board.cellToString(x, y);
                    for (EDirection direction : EDirection.values())
                        createCom(x, y, direction, board.getBuildingPlayer(x, y), -1);
                }
            }
        }
    }

    private GameResponse handleGameAction(UIAction cmd) {
        GameState actualGameState = this.gameState.clone(); // copy of the GameState & Board before attempting the action
        Board board = gameState.getBoard();
        try {
            GameAction action = cmd.getGameAction(gameState.getActualPlayer());
            RuleResult res = RuleChecker.getInstance().checkAction(board, gameState, action);
            if (res.isValid()) {
                historyGameState.push(actualGameState);
                //Communication
                computeCommunication();
                return new GameResponse(VALID, null, board, gameState.getActualPlayer());
            } else {
                return new GameResponse(INVALID, res.getLogMessage(), board, gameState.getActualPlayer());
            }
        } catch (Exception e) {
            return new GameResponse(GAME_ERROR, null, board, gameState.getActualPlayer());
        }
    }

    public GameResponse processCommand(UIAction cmd) {
        if(cmd == null)  new GameResponse(GAME_ERROR, "Error : null call.", gameState.getBoard(), gameState.getActualPlayer());
        switch (cmd.getCommand()) {
            case EXIT: {
                System.exit(0);
                break;
            }
            case LOAD: {
                LoadFile lf = new LoadFile();
                try {
                    lf.loadFile(cmd.getText());
                    computeCommunication();
                } catch (IOException e) {
                    return new GameResponse(INVALID, e.getMessage(), gameState.getBoard(), gameState.getActualPlayer());
                }
                return new GameResponse(VALID, null, gameState.getBoard(), gameState.getActualPlayer());
            }
            case SAVE: {
                break;
            }
            case REVERT: {
                //board.revert();
                //gameMaster.revert();
                if(!historyGameState.isEmpty())
                    this.gameState = historyGameState.pop();
                return new GameResponse(VALID, cmd.getErrorMessage(), gameState.getBoard(), gameState.getActualPlayer());
            }
            case END_TURN: {
                System.out.println("END TURN");
                //board.clearHistory();
                gameState.switchPlayer();
                return new GameResponse(VALID, cmd.getErrorMessage(), gameState.getBoard(), gameState.getActualPlayer());
            }
            case CMD_ERROR: {
                return new GameResponse(INVALID, cmd.getErrorMessage(), gameState.getBoard(), gameState.getActualPlayer());
            }
            case GAME_ACTION: {
                return handleGameAction(cmd);
            }
            case LIST_UNIT: {
                break;
            }
        }
        return new GameResponse(GAME_ERROR, "Error : Unimplemented call.", gameState.getBoard(), gameState.getActualPlayer());
    }
}
