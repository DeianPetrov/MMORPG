package actors;
import treasure.Weapon;
import treasure.Spell;

import static java.lang.Math.abs;

public class Enemy implements Actor {
    private final int Exp;
    private final String name;
    private  int health;
    private  int mana;
    Weapon weapon;
    Spell spell;
    public Enemy(String name, int health, int mana, Spell spell, int exp) {
        this.name = name;
        this.health = health;
        this.spell = spell;
        this.mana = mana;
        this.Exp = exp;
    }
    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMana() {
        return mana;
    }

    @Override
    public boolean isAlive() {
        return health>0;
    }

    @Override
    public Weapon getWeapon() {
        return null;
    }

    @Override
    public Spell getSpell() {
        return null;
    }

    @Override
    public void takeDamage(int damagePoints) {
        damagePoints = abs(damagePoints);
       // if(damagePoints<=0) throw new IllegalArgumentException("dmg must be positive");
         this.health -= damagePoints;
    }

    @Override
    public int attack() {
        if(spell==null && weapon==null) return 0;
        if(spell==null) return weapon.getDamage();

        if(spell.getDamage()>=weapon.getDamage()){
            if(spell.getManaCost()<=mana){
                this.mana -= spell.getManaCost();
                return spell.getDamage();
            }
        }else{
            return weapon.getDamage();
        }
        return 0;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
