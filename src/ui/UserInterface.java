package ui;

import game.board.Board;

public interface UserInterface {


    /**
     * Wait for the next user command
     * @return Command
     */
    SharedCommand getNextCommand();

    /**
     * @param response to the user
     * @param state of the game after the command
     * @return
     */
    void sendResponse(SharedCommand response, Board state);

}