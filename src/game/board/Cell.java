package game.board;

import game.EPlayer;

public class Cell implements Cloneable {
    private Unit unit;
    private Building building;
    private boolean[] communications;
    private int x, y;

    Cell(int x, int y) {
        unit = null;
        building = null;
        communications = new boolean[EPlayer.values().length];
        this.x = x;
        this.y = y;
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
            res += unit.getUnitData() + "\n";
        if (building != null)
            res += building.getBuildingData() + "\n";
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

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
