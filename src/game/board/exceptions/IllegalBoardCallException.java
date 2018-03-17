package game.board.exceptions;

public class IllegalBoardCallException extends RuntimeException {
    private String message;

    public IllegalBoardCallException(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public String toString(){
        return message;
    }
}
