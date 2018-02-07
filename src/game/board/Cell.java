package game.board;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class Cell {
    private Map<EInfoType, Info> informations;

    Cell(){
        informations = new HashMap<>();
    }

    public Info getInfo(EInfoType key){
        return informations.get(key);
    }

    public void addInfo(EInfoType key, Info i){
        informations.put(key, i);
    }

    /*
    public String toString(){
        StringBuilder res = new StringBuilder();
        Set<Map.Entry<EInfoType, Info>> setHm = informations.entrySet();
        for (Map.Entry<EInfoType, Info> e : setHm) {
            res.append(e.getKey()).append(" : ")
                    .append(e.getValue().getValue()).append("\n");
        }
        return res.toString();
    }
    */
}
