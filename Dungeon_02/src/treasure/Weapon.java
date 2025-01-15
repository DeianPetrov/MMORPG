package treasure;
import actors.Hero;
public class Weapon implements Treasure {
        private final String name;
        private final int damage;
    public Weapon(String name, int damage) {
            this.name = name;
            this.damage = damage;
    }
    @Override
    public String collect(Hero h){
        if(h==null){
             throw new IllegalArgumentException("argument is null");
        }
        if(!h.isAlive()){
             throw new IllegalArgumentException("hero is dead");
        }
        h.equip(this);
        return "Weapon found! Damage points: " + damage + " ";
    }
    public String getName(){
            return name;
    }
    public int getDamage(){
            return damage;
    }
}
