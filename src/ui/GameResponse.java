package ui;

import ui.commands.GameToUserCall;

public class GameResponse {
    private GameToUserCall response;
    private String message;

    public GameResponse(GameToUserCall response, String message) {
        this.response = response;
        this.message = message;
    }

    public GameToUserCall getResponse() {
        return response;
    }

    public void setResponse(GameToUserCall response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
