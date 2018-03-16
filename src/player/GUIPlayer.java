package player;

import ui.GameResponse;
import ui.UIAction;

public class GUIPlayer implements Player {
    private Thread gameThread;
    private Thread guiThread;
    private UIAction action;
    private GameResponse response;
    private Object wait_command;
    private Object wait_response;

    public GUIPlayer(Thread gameThread, Thread guiThread){
        this.gameThread = gameThread;
        this.guiThread = guiThread;
        action = null;
        response = null;
        wait_command = new Object();
        wait_response = new Object();
    }

    @Override
    public void setCommand(UIAction action){
        //assert Thread.currentThread() == guiThread;
        this.action = action;
        synchronized (wait_command) {
            wait_command.notifyAll();
        }
    }

    @Override
    public UIAction getCommand(){
        assert Thread.currentThread() == gameThread;
        UIAction output;
        synchronized (wait_command) {
            while (action == null) {
                try {
                    wait_command.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        output = action;
        action = null;
        return output;
    }

    @Override
    public void setResponse(GameResponse response){
        assert Thread.currentThread() == gameThread;
        this.response = response;
        synchronized (wait_response) {
            wait_response.notifyAll();
        }
    }

    @Override
    public GameResponse getResponse(){
        //assert Thread.currentThread() == guiThread;
        GameResponse output;
        synchronized (wait_response) {
            while (response == null) {
                try {
                    wait_response.wait();
                } catch (InterruptedException e) {
                }
            }
        }
        output = response;
        response = null;
        return output;
    }
}
