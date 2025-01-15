package treasure;

import java.util.ArrayList;

public class Backpack {
    private ArrayList<Treasure> treasures;
    public Backpack() {
        treasures = new ArrayList<>(10);
    }
    public void AddTreasure(Treasure treasure) {
        treasures.add(treasure);
    }
    public void ThrowThreasure(){//when the hero dies

    }
    public void EraseTreasures(){
        for(Treasure tr : treasures){
            treasures.remove(tr);
        }
    }
    public String[] showItems(){
        String[] res=new String[treasures.size()];
        for(int i = 0 ; i < treasures.size() ; i++){
            res[i]=(treasures.get(i).toString());

        }
        return res;
    }


}
