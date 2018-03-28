package player;

import ui.GameResponse;
import ui.UIAction;

public interface Player {

    UIAction getCommand();

    void setCommand(UIAction action);

    GameResponse getResponse();

    void setResponse(GameResponse response);

}
