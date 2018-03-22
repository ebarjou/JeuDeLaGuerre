import game.Game;
import player.BotPlayer;
import player.GUIPlayer;
import player.Player;
import ui.GUIThread;

public class Start {
    public static void main(String[] arg) {
        GUIThread guiThread = new GUIThread();

        Player p1 = new GUIPlayer(Thread.currentThread(), guiThread);
        Player p2 = new GUIPlayer(Thread.currentThread(), guiThread);

        if(arg.length > 0){
            if(arg[0].equalsIgnoreCase("-bot1") || arg[0].equalsIgnoreCase("-bot"))
                p1 = new BotPlayer(Thread.currentThread(), guiThread);
            else if(arg[0].equalsIgnoreCase("-bot2")) {
                p1 = new BotPlayer(Thread.currentThread(), guiThread);
                p2 = new BotPlayer(Thread.currentThread(), guiThread);
            }
        } else {
            System.out.println("You can replace player 2 by a bot using option -bot or -bot1\n" +
                                "Or you can replace both players by bot using option -bot2");
        }

        Game.init(p1, p2);

        guiThread.start();
        Game.getInstance().start();
    }
}
