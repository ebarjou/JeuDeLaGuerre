package board;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Cell {
    private Map<String, Info> informations;

    public Cell(){
        informations = new HashMap<>();
    }

    public Info getInfo(String key){
        return informations.get(key);
    }

    public void addInfo(String key, Info i){
        informations.put(key, i);
    }

    public boolean isInfo(String key){
        return informations.containsKey(key);
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
        Set<Map.Entry<String, Info>> setHm = informations.entrySet();
        for (Map.Entry<String, Info> e : setHm) {
            res.append(e.getKey()).append(" : ").append(e.getValue().getDescription()).append(" -> ").append(e.getValue().getValue()).append("\n");
            //System.out.println(e.getKey() + " : " + e.getValue());
        }
        return res.toString();
    }
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public EntityID getBuilding() {
        return building;
    }

    public void setBuilding(EntityID building) {
        this.building = building;
    }

    public EntityID getUnit() {
        return unit;
    }

    public void setUnit(EntityID unit) {
        this.unit = unit;
    }
*/

}
