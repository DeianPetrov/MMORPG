package treasure;

import actors.Hero;

public class Spell implements Treasure{
    private final String name;
    private final int damage;
    private final int manaCost;
    public Spell(String name, int damage,int manaCost) {
        this.name = name;
        this.damage = damage;//we hope the user is not retarded to make negative damage or mana cost
        this.manaCost = manaCost;//we hope the user is not retarded to make negative damage or mana cost
    }

    @Override
    public String collect(Hero h) {
        if(h==null){
            throw new IllegalArgumentException("argument is null");
        }
        if(h.isAlive()){
            throw new IllegalArgumentException("hero is dead");
        }
        h.learn(this);
        return "Spell learned! Damage points: " + damage + " ,Mana cost: " + manaCost;
    }
    public int getDamage() {
        return damage;
    }
    public int getManaCost() {
        return manaCost;
    }
    public String getName() {
        return name;
    }
}
