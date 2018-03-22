package game.board;

import game.EPlayer;
import ruleEngine.entity.EUnitData;

public class Unit implements Cloneable {
    private final EUnitData unit;
    private final EPlayer player;
    private boolean canMove;
    private int x, y;

    public Unit(EUnitData unit, EPlayer player) {
        this.unit = unit;
        this.player = player;
        canMove = true;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean getCanMove(){
        return canMove;
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    public EUnitData getUnitData() {
        return unit;
    }

    public EPlayer getPlayer() {
        return player;
    }

    @Override
    public Unit clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return (Unit) o;
    }

    public String toString(){
        return "Type " + unit + " ; player = " + player + " ; pos (" + x + ";" + y +")\nCan move : " + canMove + "\n";
    }
}
