package MapABook;

import java.util.List;
import java.util.Map;

public class MapAndNumber {
    private int index;

    private Map<String, Integer> currentMap;

    public MapAndNumber(Integer index,  Map<String, Integer> currentMap){
        this.index = index;
        this.currentMap = currentMap;
    }

    public Map<String, Integer>  getCurrentMap(){
        return currentMap;
    }
    public Integer getIndex(){
        return index;
    }
}
