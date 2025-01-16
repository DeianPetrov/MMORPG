**Heroes**

The heroes will be controlled by the client-side of the application.

For simplicity, let’s assume that up to 9 heroes can play simultaneously, each with a unique number (from 1 to 9), and they will be visualized on the map.

Heroes will be able to:

    -Move around the map
    -Collect treasures
    -Fight Minions and other players.

Each hero must have a specific level. For every battle fought and treasure collected, the hero will gain experience, and upon reaching the required experience for the next level, they will level up.

Heroes will also have a Backpack, where they can store collected treasures and optionally consume or discard them. The backpack should have a capacity of 10 items.

When a hero dies, a random item from their backpack (if any) should be dropped.

Each hero has Stats (health, mana, attack, defense). Initially, their stats should be:

    -Health: 100
    -Mana: 100
    -Attack: 50
    -Defense: 50

For every level gained, the stats should increase by:

    +10 Health
    +10 Mana
    +5 Attack
    +5 Defense

Heroes will be able to equip weapons and cast spells, which they can find and learn by collecting treasures on the map.

**Minions**

These are stationary monsters on the map that players can attack to gain experience and level up. Minions will also have a level and Stats, which will depend on their level. The higher the level of a Minion, the more experience it grants to the player who defeats it.

**Treasure**

    Weapon
    Each weapon has a level and an attack value. A hero can only fight with a weapon if their level is >= the weapon’s level. Otherwise, they can only store it in their backpack.
    When a hero equips a weapon, its attack value is added to the hero’s attack stat.

    Spell
    Similar conditions apply as with weapons, but spells additionally have a manaCost. A hero can only cast a spell if their mana is >= the spell’s manaCost.

    Mana Potion
    Contains mana points that replenish the hero’s mana.

    Health Potion
    Contains health points that heal the hero.
**THINGS LEFT TO IMPLEMENT**

    -Player combat & trade of items
    -Testing to provide code coverage 
    -Improvements on the Item distribution as well as Enemy distribution
    -Improvements on the behavior of the items
