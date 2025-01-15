import java.net.Socket;
import java.nio.*;
import java.util.*;
import java.io.*;

public class Player {
    private static final int PORT = 1234;
    private static final String SERVER_ADDRESS = "localhost"; // Server address (can be IP or hostname)
    public static void main(String[] args){
        try(Socket socket = new Socket(SERVER_ADDRESS, PORT)){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            // Send a command to the server
            String command = "";

           // String serverResponse = in.readLine();//bugna zashtoto moje bi potoka e blokiral??
            readServerResponse(in);
            while (true) {
                System.out.print("Enter command (or type 'exit' to quit): ");
                command = sc.nextLine();

                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Exiting...");//doesnt disconnect from server
                    socket.close();
                    break;
                }

                // Send the command to the server
                out.println(command);

                // Receive and print the server response
                String serverResponse ;
                while ((serverResponse = in.readLine()) != null) {
                    if (serverResponse.equals("END")) { // Check for the end of the response
                        break;
                    }
                    System.out.println(serverResponse);
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static void readServerResponse(BufferedReader in) throws IOException {
        String serverResponse;
        while ((serverResponse = in.readLine()) != null) {
            if (serverResponse.equals("END")) { // Look for the END marker
                break;
            }
            System.out.println(serverResponse);
        }
    }
}
