package ui;

public interface IUserCommand {


    /**
     * Wait for the next user command
     * @return Command
     */
    String ask();

    /**
     * @param response to the user
     * @return
     */
    void respond(String response);

}
