package game.board;

import game.EPlayer;
import game.EPlayer.*;

class Cell implements Cloneable {
    private Unit unit;
    private Building building;
    private boolean[] communications;

    Cell() {
        unit = null;
        building = null;
        communications = new boolean[EPlayer.values().length];
        for(int i = 0; i < communications.length; i++)
            communications[i] = false;
    }

    @Override
    public Cell clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assert o != null;

        ((Cell) o).building = null;
        if (building != null)
            ((Cell) o).building = building.clone();

        ((Cell) o).unit = null;
        if (unit != null)
            ((Cell) o).unit = unit.clone();

        return (Cell) o;
    }

    public String toString() {
        String res = "";
        if (unit != null)
            res += unit.getUnit() + "\n";
        if (building != null)
            res += building.getBuilding() + "\n";
        if (res.isEmpty())
            return res;

        return res + "Com1 : " + communications[EPlayer.PLAYER1.ordinal()] + "\nCom2: " + communications[EPlayer.PLAYER2.ordinal()] + "\n";
    }

    public Unit getUnit() {
        return unit;
    }

    void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Building getBuilding() {
        return building;
    }

    void setBuilding(Building building) {
        this.building = building;
    }

    void setCommunication(EPlayer player, boolean communication){
        communications[player.ordinal()] = communication;
    }

    public boolean isCommunication(EPlayer player) {
        return communications[player.ordinal()];
    }
}
