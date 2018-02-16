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
}
