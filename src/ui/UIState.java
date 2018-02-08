package ui;

public class UIState {
    private boolean game_started;
    private int player_turn;
    private boolean state_setup;
    private boolean game_over;
    private boolean wait_resp;
    private boolean wait_input;

    public UIState() {
        this.game_started = false;
        this.state_setup = false;
        this.game_over = false;
        this.wait_input = false;
        this.wait_resp = false;
        this.player_turn = -1;
    }

    public void set(int state){

        return;
    }


}
