package game;

import game.board.Board;
import game.board.BoardManager;
import game.board.IBoardManager;
import game.board.entity.EBuilding;
import game.board.entity.EUnit;
import game.board.BoardManager;
import ruleEngine.EGameActionType;
import ruleEngine.GameAction;
import ruleEngine.RuleChecker;
import ui.CommandException;
import ui.SharedCommand;
import ui.UserInterface;

import static ui.commands.GameToUserCall.*;

/*
 * Peut-être à mettre en tant que Singleton ?
 */
public class Game {
    private IBoardManager boardManager;
    private UserInterface ui;

    public Game(UserInterface ui){
        this.ui = ui;
        boardManager = BoardManager.getInstance();
        ui.sendResponse(new SharedCommand(REFRESH), boardManager.getBoard());
    }

    public void startNewGame(){
        gameLoop();
    }

    private void initGame(){

    }

    private void gameLoop(){
        boolean quit = false;
        while (!quit){
            SharedCommand cmd = ui.getNextCommand();
            switch (cmd.getCommand()){
                case EXIT:{
                    quit = true;
                    cmd.setResponse(VALID);
                    break;
                }
                case LOAD:{
                    break;
                }
                case SAVE:{
                    break;
                }
                case REVERT:{
                    break;
                }
                case CMD_ERROR:{
                    cmd.setResponse(GAME_ERROR);
                    ui.sendResponse(cmd, null);
                    continue;
                }
                case PAT:{
                    break;
                }
                case SURRENDER:{
                    break;
                }
                default:{
                    //if ruleEngine.isValid(cmd)
                    //then boardManager.apply(cmd)
                    //     cmd.setResponse(VALID)
                    try {
                        int[] a = cmd.getCoords1();
                        int[] b = cmd.getCoords2();
                        System.out.println("GAME : From [" + a[0] + "," + a[1] + "] To [" + b[0] + "," + b[1] + "]");
                        cmd.setResponse(VALID);
                    } catch (Exception e) {
                        cmd.setResponse(GAME_ERROR);
                    }
                    break;
                }
            }
            ui.sendResponse(cmd, boardManager.getBoard());
        }
    }

}
