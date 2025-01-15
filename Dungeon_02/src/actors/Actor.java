package actors;

import treasure.Weapon;
import treasure.Spell;

public interface Actor {
    int getHealth();
    String getName();
    int getMana();
    boolean isAlive();
    Weapon getWeapon();
    Spell getSpell();
    void takeDamage(int damagePoints);
    int attack();
    void setHealth(int health);
}

