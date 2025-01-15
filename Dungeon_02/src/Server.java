import actors.Actor;
import actors.Enemy;
import actors.Hero;
import treasure.Spell;
import treasure.Treasure;
import treasure.Weapon;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.nio.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 1234;
    private static final int USERS_LIMIT = 9 ;
    private Map<Integer, Actor> players = new HashMap<>();
    private List<PlayerHandler> clients = new ArrayList<>();
    private static final ConcurrentHashMap<Socket, Thread> activeClients = new ConcurrentHashMap<>();//za sledene na playeri
    private static GameEngine gameEngine;
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(USERS_LIMIT); // Create a thread pool
        try(ServerSocket serverSocket = new ServerSocket(PORT)){//suzdavame socketa
            char[][] map = {
                    {'#', '#', '#', '#', '#', '#'},
                    {'#', '.', '.', '.', 'T', '#'},
                    {'#', '.', '#', '.', 'E', '#'},
                    {'#', '.', '.', '.', '.', '#'},
                    {'#', '#', '#', '#', '#', '#'}
            };

            ArrayList<Enemy> enemies = new ArrayList<>();
            enemies.add(new Enemy("Goblin", 50, 10,new Spell("frostnova",10,10),50));
            ArrayList<Treasure> treasures = new ArrayList<>();

            treasures.add(new Weapon("sword", 100));

            gameEngine = new GameEngine(map, enemies, treasures);
            while (true) {
                Socket clientSocket = serverSocket.accept();

                if (activeClients.size() >= USERS_LIMIT) {
                    System.out.println("Connection limit reached. Rejecting client: " + clientSocket.getInetAddress());
                    clientSocket.close();
                } else {
                    Hero h = new Hero("bob" + clientSocket.getInetAddress());
                    gameEngine.placeHeroOnEmptyTile(h);

                    // Submit the PlayerHandler task to the thread pool
                    threadPool.submit(() -> {
                        try {
                            PlayerHandler handler = new PlayerHandler(clientSocket, gameEngine, h);
                            handler.run(); // Start handling the client
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            activeClients.remove(clientSocket);
                            try {
                                clientSocket.close();
                            } catch (IOException ignored) {
                            }
                        }
                    });

                    activeClients.put(clientSocket, Thread.currentThread());
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();

        }
        finally{
            threadPool.shutdown();
        }
    }
}
