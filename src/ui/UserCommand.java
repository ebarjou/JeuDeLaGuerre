package ui;

public interface UserCommand {


    /**
     * Wait for the next user command
     * @return Command
     */
    SharedCommand getNextCommand();

    /**
     * @param response to the user
     * @return
     */
    void sendResponse(SharedCommand response);

    void setState();

}
