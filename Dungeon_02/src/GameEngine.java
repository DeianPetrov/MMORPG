import treasure.*;
import actors.*;

import java.util.ArrayList;
import java.util.HashMap;

public class GameEngine {
    private char[][] map;
    private ArrayList<Enemy> enemies;
    private ArrayList<Treasure> treasures;
    private HashMap<Hero, int[]> heroPositions;

    private int HeroXPos;
    private int HeroYPos;
    public GameEngine(char[][] map,  ArrayList<Enemy> enemies,ArrayList<Treasure> treasures){
        this.map = map;
        this.enemies = enemies;
        this.treasures = treasures;
        heroPositions = new HashMap<>();
    }

    private void FindHeroPosition(){
        for(int i = 0;i<map.length;i++){
            for(int j = 0;j<map[i].length;j++){
                if(map[i][j] == '.'){
                    HeroXPos = i;
                    HeroYPos = j;
                    map[i][j] = 'H';
                }
            }
        }
    }
    public char[][] getMap(){
        return map;
    }

    public int[] getHeroPosition(){
        return new int[]{HeroXPos, HeroYPos};
    }
    public synchronized  void placeTreasureOnEmptyTile(ArrayList<Treasure> itemsOnMap,Treasure treasure){
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j] == '.'){
                    itemsOnMap.add(treasure);
                    map[i][j]='T';
                    return;

                }
            }
        }

    }
    public void PlaceTreasure(){
        if(treasures.isEmpty()){
            placeTreasureOnEmptyTile(treasures,new Weapon("pike",15));
        }
    }
    public synchronized boolean placeHeroOnEmptyTile(Hero hero) {// sus celta da ne se overlap vat
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '.') {
                    map[i][j] = 'H'; // Mark the map with the hero
                    heroPositions.put(hero, new int[]{i, j}); // Store hero's position
                    return true;
                }
            }
        }
        return false; // No empty tile available
    }

    public synchronized String makeMove(Hero hero, Direction direction) {
        if (!heroPositions.containsKey(hero)) {
            return "Hero is not in the game!";
        }

        int[] pos = heroPositions.get(hero);
        int x = pos[0];
        int y = pos[1];

        int newX = x, newY = y;
        switch (direction) {
            case UP -> newX--;
            case DOWN -> newX++;
            case LEFT -> newY--;
            case RIGHT -> newY++;
        }

        if (!BoundCheck(newX, newY)) {
            return "Move out of bounds!";
        }

        switch (map[newX][newY]) {
            case '.' -> {
                updateHeroPosition(hero, x, y, newX, newY);
                return "You moved successfully!";
            }
            case '#' -> {
                return "You hit a wall!";
            }
            case 'T' -> {
               // String message = treasures.get(0).collect(hero); // Assume treasures are in a list
                hero.PickUpTreasure(treasures.getFirst());

                treasures.removeFirst();

                updateHeroPosition(hero, x, y, newX, newY);
                PlaceTreasure();
                return "treasure picked by " + hero.getName();
            }
            case 'E' -> {
                Enemy enemy = enemies.getFirst(); // Assume enemies are in a list
                String result = battle(hero, enemy);
                if (hero.isAlive()) {
                    updateHeroPosition(hero, x, y, newX, newY);
                }
                return result;
            }
            case 'G' -> {
                updateHeroPosition(hero, x, y, newX, newY);
                return "You reached the goal!";
            }
            case 'H' -> {
                return "Another hero is already at this position!";
            }
            default -> {
                return "Unknown action!";
            }
        }
    }
    public synchronized void ClearOnExit(Hero h){
        int[] vals = heroPositions.get(h);
        map[vals[0]][vals[1]] = '.';
    }
    private void updateHeroPosition(Hero hero, int oldX, int oldY, int newX, int newY) {
        map[oldX][oldY] = '.'; // Mark the old position as empty
        map[newX][newY] = 'H'; // Mark the new position with the hero
        heroPositions.put(hero, new int[]{newX, newY}); // Update hero's position
    }
    private boolean BoundCheck(int x, int y){
        return x >= 0 && x < map.length && y >= 0 && y < map[0].length;
    }
    private String MoveBehavior(int xPos, int yPos,Hero hero){

        switch (map[xPos][yPos]){
            case 'S':
            case '.':
                map[xPos][yPos] = 'H';
                map[HeroXPos][HeroYPos] = '.';
                HeroXPos = xPos;
                HeroYPos = yPos;
                return "You moved successfully to the next position.";
            case '#':
                return "Wrong move. There is an obstacle and you cannot bypass it.";
            case 'T':
                String message = treasures.getFirst().collect(hero);
                treasures.removeFirst();
                map[xPos][yPos] = 'H';
                map[HeroXPos][HeroYPos] = '.';
                HeroXPos = xPos;
                HeroYPos = yPos;
                updateHeroPosition(hero, xPos, yPos, HeroXPos, HeroYPos);
                return ("treasure obtained"+hero.getName());

            case 'E':
                while(hero.isAlive()&&enemies.getFirst().isAlive()){
                    enemies.getFirst().takeDamage(hero.attack());
                    if(enemies.getFirst().isAlive()){
                        hero.takeDamage(enemies.getFirst().attack());
                    }
                }
                if(hero.isAlive()){
                    enemies.removeFirst();
                    hero.SetExp(50);
                    hero.LevelUp();
                    map[xPos][yPos] = 'H';
                    map[HeroXPos][HeroYPos] = '.';
                    HeroXPos = xPos;
                    HeroYPos = yPos;
                    return "enemy died";
                }
                else{
                    return "hero is dead , game over";
                }
            case 'G':
                return  "You have successfully passed through the dungeon. Congrats!";
        }
        return "unknown command";
    }
    private String battle(Hero hero, Enemy enemy) {//po umno ot moeto
        while (hero.isAlive() && enemy.isAlive()) {
            enemy.takeDamage(hero.attack());
            if (enemy.isAlive()) {
                hero.takeDamage(enemy.attack());
            }
        }
        if (hero.isAlive()) {
            enemies.remove(enemy);
            hero.LevelUp();
            return "You defeated the enemy!";

        } else {
            hero.setHealth(0);
            return "You died! Game over.";
        }
    }
    public String[] showItems(Hero h){
       return h.getBackpack().showItems();
    }
}
