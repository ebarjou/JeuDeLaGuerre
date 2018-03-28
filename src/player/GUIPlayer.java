package player;

import ui.GameResponse;
import ui.UIAction;

public class GUIPlayer implements Player {
    private UIAction action;
    private GameResponse response;
    private Object wait_command;
    private Object wait_response;

    public GUIPlayer() {
        action = null;
        response = null;
        wait_command = new Object();
        wait_response = new Object();
    }

    @Override
    public UIAction getCommand() {
        UIAction output;
        synchronized (wait_command) {
            while (action == null) {
                try {
                    wait_command.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        output = action;
        action = null;
        return output;
    }

    @Override
    public void setCommand(UIAction action) {
        this.action = action;
        synchronized (wait_command) {
            wait_command.notifyAll();
        }
    }

    @Override
    public GameResponse getResponse() {
        GameResponse output;
        synchronized (wait_response) {
            while (response == null) {
                try {
                    wait_response.wait();
                } catch (InterruptedException ignored) {
                }
            }
        }
        output = response;
        response = null;
        return output;
    }

    @Override
    public void setResponse(GameResponse response) {
        this.response = response;
        synchronized (wait_response) {
            wait_response.notifyAll();
        }
    }
}
