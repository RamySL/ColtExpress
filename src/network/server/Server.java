package network.server;

/**
 * Le serveur doit prendre tous les parametres du jeu (nb de manches, balles etc) et le nombre de joueur qu'il va y'avoir
 * Donc les clients sont en attente tant que le nombre de joueur conecté n'est pas atteint, les clients sont stockés dans une
 * liste, la communication avec chaque un des clients se fait par l'intermediaire d'un thread (multi threading)
 */

import network.PaquetNbJoeurConnecte;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Classe principale du serveur
 * Lance le serveur qui attend la connection d'un nombre donné de clients, pour chaque client la communication est
 * gérée à travers un Thread dans la colections de threads (pool)
 */
public class Server {
    private final int port;
    private final int maxPlayers;
    private final List<ClientHandler> players;
    private final ExecutorService pool;

    private int nbJoueurConnecte;

    public Server(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(maxPlayers);
        this.nbJoueurConnecte = 0;
    }

    public void start() {
        System.out.println("Game server starting...");
        // the 0 for backlog sets the limit to default
        // le 0.0.0.0 ip lie le serveur à tous les ip local de la machine
        try (ServerSocket listener = new ServerSocket(port, 0, InetAddress.getByName("0.0.0.0"))) {
            while (players.size() < maxPlayers) {
                System.out.println("Waiting for players to connect...");
                Socket client = listener.accept();
                System.out.println("Player connected!");
                this.nbJoueurConnecte ++;
                ClientHandler player = new ClientHandler(client);

                players.add(player);
                // player est runnable et execute() execute son run
                pool.execute(player);

                for (ClientHandler p : players) {
                    p.updateNbPlayerConnected();
                }
            }
            System.out.println("All players connected. Starting the game...");
            startGame();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    private void startGame() {
        // Notify all players that the game is starting
        for (ClientHandler player : players) {
            //player.sendMessage("Game is starting!");

        }
        // Implement the game logic and loop here
    }

//    private void broadcastMovement(String movement) {
//        for (ClientHandler player : players) {
//            player.sendMessage(movement);
//        }
//    }

    public int getNbJoueurConnecte() {
        return nbJoueurConnecte;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    private class ClientHandler implements Runnable {
        private final Socket client;
        private ObjectOutputStream out;
        private ObjectInputStream in;

        public ClientHandler(Socket clientSocket) throws IOException {

            this.client = clientSocket;
            this.updateNbPlayerConnected();

            out = new ObjectOutputStream(client.getOutputStream()); // true permet d'envoyer directement sans attendre le remplissage du buffer
            in = new ObjectInputStream(client.getInputStream());
        }

        @Override
        public void run() {
            try {

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received movement: " + message);

                    //broadcastMovement(message); // Broadcast the movement to all players
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void updateNbPlayerConnected () throws IOException {
            if (out != null) {
                out.writeObject(new PaquetNbJoeurConnecte(maxPlayers - nbJoueurConnecte));
            }
        }
    }
}