package network.client;

import controleur.ControleurServerClient;
import network.PaquetChoixJrHost;
import network.PaquetNbJoeurConnecte;
import network.server.PaquetChoixJrClient;

import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;

public class Client {
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private BufferedReader userInput;
    private ControleurServerClient cntrlServerClient;

    boolean host = false;

    public Client(String serverAddress, int serverPort, ControleurServerClient ctrlServerClient) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.cntrlServerClient = ctrlServerClient;
    }

    public void start() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Connected to the game server.");

            // Create a thread to listen for messages from the server
            Thread listenerThread = new Thread(new ServerListener());
            listenerThread.start();

            // Main loop to send player movements to the server
            String movement;
            while ((movement = userInput.readLine()) != null) {
                if (isValidMovement(movement)) {
                    //out.println(movement);
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
                Object serverMessage;
                while ((serverMessage = in.readObject()) != null) {
                    if (serverMessage instanceof PaquetNbJoeurConnecte) {
                        PaquetNbJoeurConnecte p = (PaquetNbJoeurConnecte) serverMessage;
                        cntrlServerClient.updateNbJoueurConnecte(p.getNbJoueurRestants());

                    }else if (serverMessage instanceof PaquetChoixJrClient){
                        // vue sans param ( un extend ?)
                        cntrlServerClient.vueClient();
                    }else if (serverMessage instanceof PaquetChoixJrHost){
                        host = true;
                        // copier coller de la vue des param
                        cntrlServerClient.vueHost();
                    }



                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        }
    }


}
