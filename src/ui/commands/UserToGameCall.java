package ui.commands;

public enum UserToGameCall {
    CMD_ERROR(),
    GAME_ACTION(),
    LIST_UNIT(),
    SAVE(),
    LOAD(),
    REVERT(),
    END_TURN(),
    EXIT(),
    REFRESH();
}
