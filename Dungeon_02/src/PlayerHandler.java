import actors.Hero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


public class PlayerHandler implements Runnable {
    private Socket clientSocket;
    private GameEngine gameEngine;
    private Hero hero;
    private static ArrayList<PlayerHandler> players = new ArrayList<>();
    public PlayerHandler(Socket clientSocket, GameEngine gameEngine, Hero hero) {
        this.clientSocket = clientSocket;
        this.gameEngine = gameEngine;
        this.hero = hero;
        players.add(this);
    }

    @Override
    public void run() {
        try (

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            // Send initial map to the client

            out.println(renderMap(gameEngine.getMap()));
            out.println("END"); // End the response with "END"
            String input;//za komandite na playera
            while ((input = in.readLine()) != null) {
                // Parse input (e.g., "MOVE UP")
                String[] command = input.split(" ");
                if (command[0].equalsIgnoreCase("MOVE")) {
                    Direction direction = Direction.valueOf(command[1].toUpperCase());
                    String response = gameEngine.makeMove(hero,direction);

                    // Send updated map and response to the client
                    out.println(response);
                    out.println(renderMap(gameEngine.getMap()));
                    out.println("END"); // End the response with "END"
                }
                else if(command[0].equalsIgnoreCase("BACKPACK")) {
                    String[] arr = this.hero.getBackpack().showItems();
                    for(int i = 0; i < arr.length; i++) {
                        out.println(arr[i]);

                    }

                    out.println("END");
                }
                else if(command[0].equalsIgnoreCase("EXIT")){
                    gameEngine.ClearOnExit(this.hero);
                    clientSocket.close();
                }
                else {
                    out.println("Unknown command.");
                    out.println("END"); // End the response with "END"
                }
            }
        } catch (IOException e) {
            System.err.println("Client disconnected.");
            gameEngine.ClearOnExit(this.hero);
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private synchronized String renderMap(char[][] map) {
        StringBuffer sb = new StringBuffer();
        for (char[] row : map) {
            for (char cell : row) {
                sb.append(cell).append(" ") ;
            }

           // sb.append(" ");  // Add a space after each row
           sb.append('\n');  // Add a newline after the row
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}