package treasure;

import actors.Hero;

public class HealthPotion implements Treasure{

    private final int healthPoints;
    HealthPotion(int manaPoints) {
        if (manaPoints > 0)this.healthPoints = manaPoints;
        else this.healthPoints = 10;//cuz i said so
    }

    public int heal(){
        return this.healthPoints;
    }
    @Override
    public String collect(Hero h) {
        if(h==null){
            throw new IllegalArgumentException("argument is null");
        }
        if(!h.isAlive()){
            throw new IllegalArgumentException("hero is not alive");
        }
        h.takeHealing(this.healthPoints);
        return "Mana potion found!"+ healthPoints+" mana points added to your hero!";

    }
}
