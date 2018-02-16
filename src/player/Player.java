package player;

import ui.GameResponse;
import ui.UIAction;

public interface Player {

    void setCommand(UIAction action);
    UIAction getCommand();

    void setResponse(GameResponse response);
    GameResponse getResponse();

}
