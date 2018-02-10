package ruleEngine;

import game.EPlayer;

public class GameAction {
    private EGameActionType actionType;
    private EPlayer player;
    private Coordinates sourceCoordinates;
    private Coordinates targetCoordinates;

    public GameAction(EPlayer player, EGameActionType actionType){
        this.player = player;
        this.actionType = actionType;
    }

    public EGameActionType getActionType() {
        return actionType;
    }

    public void setActionType(EGameActionType actionType) {
        this.actionType = actionType;
    }

    public Coordinates getSourceCoordinates() {
        return sourceCoordinates;
    }

    public void setSourceCoordinates(int x, int y) {
        this.sourceCoordinates = new Coordinates(x, y);
    }

    public Coordinates getTargetCoordinates() {
        return targetCoordinates;
    }

    public void setTargetCoordinates(int x, int y) {
        this.targetCoordinates = new Coordinates(x, y);
    }

    public EPlayer getPlayer() {
        return player;
    }

    public void setPlayer(EPlayer player) {
        this.player = player;
    }

    public Coordinates createCoordinates(int x, int y){
        return new Coordinates(x, y);
    }

    public class Coordinates {
        private int x;
        private int y;

        Coordinates(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
