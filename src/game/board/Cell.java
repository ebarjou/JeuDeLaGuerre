package game.board;

import game.EPlayer;

class Cell implements Cloneable {
    private Unit unit;
    private Building building;
    private boolean communication1;
    private boolean communication2;

    Cell() {
        unit = null;
        building = null;
        communication1 = false;
        communication2 = false;
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

        return res + "Com1 : " + communication1 + "\nCom2: " + communication2 + "\n";
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setCommunication1(boolean communication1) {
        this.communication1 = communication1;
    }

    public void setCommunication2(boolean communication2) {
        this.communication2 = communication2;
    }

    public boolean getCommunication(EPlayer player) {
        if (player == EPlayer.PLAYER1)
            return communication1;
        return communication2;
    }
}
