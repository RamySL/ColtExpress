package client;

import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;

public class Client {
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private BufferedReader userInput;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to the game server.");

            // Create a thread to listen for messages from the server
            Thread listenerThread = new Thread(new ServerListener());
            listenerThread.start();

            // Main loop to send player movements to the server
            String movement;
            while ((movement = userInput.readLine()) != null) {
                if (isValidMovement(movement)) {
                    out.println(movement);
                } else {
                    System.out.println("Invalid movement command. Use: up, down, left, right");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    private boolean isValidMovement(String movement) {
        return movement.equalsIgnoreCase("up") ||
                movement.equalsIgnoreCase("down") ||
                movement.equalsIgnoreCase("left") ||
                movement.equalsIgnoreCase("right");
    }

    private void closeConnections() {
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
            if (userInput != null) userInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = in.readLine()) != null) {
                    System.out.println("Server: " + serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        }
    }


}
