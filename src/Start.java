import game.board.Board;
import game.board.BoardMaster;
import game.board.IBoardMaster;
import ui.TermUI;
import ui.UserInterface;

public class Start {

    public static void main(String[] arg){
        IBoardMaster bm = BoardMaster.getInstance();

        UserInterface ui = new TermUI();
        while(true){
            System.out.println(ui.getNextCommand().getCommand());
        }
        //System.out.print("debord");
    }
}
