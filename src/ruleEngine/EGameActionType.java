package ruleEngine;

/**
 * Designs an action performed on the game.
 *
 * @see game.Game
 */
public enum EGameActionType {
    /**
     * Move a unit on the board.
     */
    MOVE,
    /**
     * Attack with a unit on the board.
     */
    ATTACK,
    /**
     * Special attack with units on the board. Currently shadowed by ATTACK if possible.
     */
    CHARGE,
    /**
     * Ask the opponent for a draw.
     */
    PROPOSE_DRAW,
    SET,
    /**
     * End the turn.
     */
    END_TURN,
    /**
     * Let the opponent win the game.
     */
    SURRENDER,
    /**
     * Ask for the communications to be computed.
     */
    COMMUNICATION,
    /**
     * Empty action.
     */
    NONE,
    //REVERT,
}
