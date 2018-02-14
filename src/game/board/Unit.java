package game.board;

import game.EPlayer;
import ruleEngine.entity.EUnitData;

public class Unit implements Cloneable {
    private EUnitData unit;
    private EPlayer player;

    public Unit(EUnitData unit, EPlayer player) {
        this.unit = unit;
        this.player = player;
    }

    public EUnitData getUnit() {
        return unit;
    }

    void setUnit(EUnitData unit) {
        this.unit = unit;
    }

    public EPlayer getPlayer() {
        return player;
    }

    void setPlayer(EPlayer player) {
        this.player = player;
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
}
