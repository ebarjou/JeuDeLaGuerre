package ui;

public class CurrentGameState {
    /*
     * TODO : move to game
     */
    private boolean game_started;
    private int player_turn;
    private int player_asked_pat;
    private boolean state_setup;
    private boolean game_over;
    private boolean wait_resp;
    private boolean wait_input;


    public CurrentGameState() {
        this.game_started = false;
        this.state_setup = false;
        this.game_over = false;
        this.wait_input = false;
        this.wait_resp = false;
        this.player_turn = -1;
        this.player_asked_pat = -1;
    }

    public void set(int state){

        return;
    }


}
