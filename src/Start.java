import ui.TermUI;
import ui.UserInterface;

public class Start {

    public static void main(String[] arg){
        UserInterface ui = new TermUI();
        while(true){
            System.out.println(ui.getNextCommand().getCommand());
        }
        //System.out.print("debord");
    }
}
