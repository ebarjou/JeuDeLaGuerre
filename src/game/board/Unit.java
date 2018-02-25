package game.board;

import game.EPlayer;
import ruleEngine.GameAction;
import ruleEngine.entity.EUnitData;

public class Unit implements Cloneable {
    private EUnitData unit;
    private EPlayer player;
    private boolean canMove, canAttack;
    private int x, y;

    public Unit(EUnitData unit, EPlayer player) {
        this.unit = unit;
        this.player = player;
        canMove = true;
        canAttack = true;
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

    public boolean getCanAttack(){
        return canAttack;
    }

    public void setCanMove(boolean canMove){
        this.canMove = canMove;
    }

    public void setCanAttack(boolean canAttack){
        this.canAttack = canAttack;
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
        return "Type " + unit + " ; player = " + player + " ; pos (" + x + ";" + y +")\nCan attack : " + canAttack
                + "\nCan move : " + canMove + "\n";
    }
}
