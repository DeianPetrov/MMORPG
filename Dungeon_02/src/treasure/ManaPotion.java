package treasure;

import actors.Hero;

public class ManaPotion implements Treasure{

    private final int manaPoints;
    ManaPotion(int manaPoints) {
        if (manaPoints > 0)this.manaPoints = manaPoints;
        else this.manaPoints = 10;//cuz i said so
    }
    public int heal() {
        return manaPoints;
    }
    @Override
    public String collect(Hero h) {
        if(h==null){
            throw new IllegalArgumentException("argument is null");
        }
        if(!h.isAlive()){
            throw new IllegalArgumentException("hero is not alive");
        }
        h.takeMana(this.manaPoints);
        return "Mana potion found!"+ manaPoints+" mana points added to your hero!";
    }
}
