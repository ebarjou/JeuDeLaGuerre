package ui;

import game.EPlayer;
import game.board.IBoard;
import ui.commands.GameToUserCall;

public class GameResponse {
    private GameToUserCall response;
    private String message;
    private IBoard board;
    private EPlayer player;

    public GameResponse(GameToUserCall response, String message, IBoard board, EPlayer player) {
        this.response = response;
        this.message = message;
        this.board = board;
        this.player = player;
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

    public EPlayer getPlayer() {
        return player;
    }

    public IBoard getBoard() {
        return board;
    }
}
