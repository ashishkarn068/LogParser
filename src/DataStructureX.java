import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStructureX {

    private Map<Integer, Integer> dataStore;
    private List<Integer> datalist;
    private int maxRange;



    public int getRandom(){
        Double randomPosition = Math.random()%maxRange;
        int randValue = this.datalist.get(randomPosition.intValue());
        return randValue;
    }

    public void remove(int keyToRemove){
        if (!dataStore.containsKey(keyToRemove)){
            return;
        }
        int index = dataStore.get(keyToRemove);
        dataStore.remove(keyToRemove);
        datalist.add(index,datalist.get(maxRange-1));
        maxRange -= 1;

    }

    public void insert(int keyToInsert){
        if (dataStore.containsKey(keyToInsert)){
            return;
        }
        dataStore.put(keyToInsert,maxRange);
        datalist.add(maxRange, keyToInsert);
        maxRange++;
    }

    public DataStructureX(){
        this.dataStore = new HashMap<>();
        this.datalist = new ArrayList<>();
        this.maxRange = 0;
    }

    public static void main(String[] args) {
        DataStructureX dataStruct = new DataStructureX();

        dataStruct.insert(5);
        dataStruct.insert(6);
        dataStruct.insert(20);
        dataStruct.insert(8);
        dataStruct.remove(6);
                dataStruct.remove(8);
                dataStruct.remove(6);
        dataStruct.insert(5);
                int random = dataStruct.getRandom();
        System.out.println(random);
    }
}
