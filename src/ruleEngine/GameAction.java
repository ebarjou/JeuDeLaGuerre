package ruleEngine;

import game.EPlayer;

/**
 * Wrapper object containing all the information about an action on the game.
 */
public class GameAction {
    private EGameActionType actionType;
    private EPlayer player;
    private Coordinates sourceCoordinates;
    private Coordinates targetCoordinates;

    public GameAction(EPlayer player, EGameActionType actionType) {
        this.player = player;
        this.actionType = actionType;
    }

    /**
     * @return The type of the action performed.
     */
    public EGameActionType getActionType() {
        return actionType;
    }

    /**
     * @param actionType The type of the action to be performed.
     */
    public void setActionType(EGameActionType actionType) {
        this.actionType = actionType;
    }

    /**
     * @return The coordinates from where the action is performed.
     */
    public Coordinates getSourceCoordinates() {
        return sourceCoordinates;
    }

    /**
     * @param x The column index from where the action is performed.
     * @param y The row index from where the action is performed.
     */
    public void setSourceCoordinates(int x, int y) {
        this.sourceCoordinates = new Coordinates(x, y);
    }

    /**
     * @return The coordinates to where the action is performed.
     */
    public Coordinates getTargetCoordinates() {
        return targetCoordinates;
    }

    /**
     * @param x The column index to where the action is performed.
     * @param y The row index to where the action is performed.
     */
    public void setTargetCoordinates(int x, int y) {
        this.targetCoordinates = new Coordinates(x, y);
    }

    /**
     * @return The player requesting the action to be performed.
     */
    public EPlayer getPlayer() {
        return player;
    }

    /**
     * @param player The player requesting the action.
     */
    public void setPlayer(EPlayer player) {
        this.player = player;
    }

}
