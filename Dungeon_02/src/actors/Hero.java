package actors;
import treasure.Spell;
import treasure.Treasure;
import treasure.Weapon;
import treasure.Backpack;
public class Hero implements Actor{
    private int level = 1;
    private final String name;
    private int EXP=0;
    private  int mana = 100;
    private  int hp = 100;
    private int attack=50;
    private int defense=50;
    /*********************************
     STATS LIMITS --> WILL MAKE BETTER IMPLEMENTATION
     */
    private  int manaLimit = 100;
    private  int hpLimit = 100;
    private int attackLimit=50;
    private int defenseLimit=50;

    /*********************************/
    private Backpack backpack=null ;
    private Weapon weapon = null;
    private Spell spell = null;
    public Hero(String name) {
        this.name = name;
        this.backpack = new Backpack();
    }
    public Backpack getBackpack() {
        return backpack;
    }
    public void PickUpTreasure(Treasure treasure){
        backpack.AddTreasure(treasure);
    }

    @Override
    public int getHealth() {
        return  this.hp;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public boolean isAlive() {
        return hp>0;
    }

    @Override
    public Weapon getWeapon() {
        return this.weapon;
    }

    @Override
    public Spell getSpell() {
        return this.spell;
    }

    @Override
    public void takeDamage(int damagePoints) {
        if(damagePoints>0){
            this.hp -= damagePoints;
        }else throw new IllegalArgumentException("Damage points should be positive");
    }

    @Override
    public int attack() {
        if(spell==null && weapon==null) return this.attack;
        if(spell==null) return weapon.getDamage();

        if(spell.getDamage()>=weapon.getDamage()){
            if(spell.getManaCost()<=mana){
                this.mana -= spell.getManaCost();
                return spell.getDamage();
            }
        }else{
            return weapon.getDamage()+this.attack;
        }
        return 0;
    }

    @Override
    public void setHealth(int health) {
        this.hp = health;
    }

    public void equip(Weapon wp){
        if(wp==null){
            throw new IllegalArgumentException("Argument is null");
        }
        if(this.weapon==null){
            this.weapon = wp;
            return;
        }
        if(wp.getDamage()>this.weapon.getDamage()){
            this.weapon = wp;
        }

    }
   public  void takeMana(int manaPoints){
        if(manaPoints>0 &&  this.mana + manaPoints<=manaLimit){
            this.mana+=manaPoints;
        }else if(this.mana + manaPoints>manaLimit){
            this.mana = manaLimit;
        }else throw new IllegalArgumentException("Argument is negative");
    }
    public void takeHealing(int healingPoints){
        if(healingPoints>0 && this.hp + healingPoints<=hpLimit){
            this.hp += healingPoints;
        }else if(this.hp + healingPoints>hpLimit){
            this.hp = hpLimit;
        }else throw new IllegalArgumentException("Argument is negative");
    }
    public void learn(Spell sp){
        if(sp==null){
            throw new IllegalArgumentException("Argument is null");
        }
        if(this.spell==null){
            this.spell = sp;
        }
        else{
            if(this.spell.getDamage()>sp.getDamage()) this.spell = sp;
        }
    }
    public void LevelUp(){
        if(EXP % (100*level)==0){
            level++;
            manaLimit +=10;
            hpLimit+=10;
            attackLimit +=5;
            defenseLimit +=5;
        }
    }
    public void SetExp(int exp){
        if(exp>0){
            this.EXP += exp;
        }
    }
    //public void TradeTreasure(Hero other){
    //    if()
   // }

}
