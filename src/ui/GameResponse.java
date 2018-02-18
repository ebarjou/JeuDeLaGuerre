package ui;

import game.EPlayer;
import game.board.Board;
import ui.commands.GameToUserCall;

public class GameResponse {
    private GameToUserCall response;
    private String message;
    private Board board;
    private EPlayer player;

    public GameResponse(GameToUserCall response, String message, Board board, EPlayer player) {
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

    public Board getBoard() {
        return board;
    }
}
