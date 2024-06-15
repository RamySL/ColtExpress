package server;

/**
 * Le serveur doit prendre tous les parametres du jeu (nb de manches, balles etc) et le nombre de joueur qu'il va y'avoir
 * Donc les clients sont en attente tant que le nombre de joueur conecté n'est pas atteint, les clients sont stockés dans une
 * liste, la communication avec chaque un des clients se fait par l'intermediaire d'un thread (multi threading)
 */

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

    public Server(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(maxPlayers);
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
                ClientHandler player = new ClientHandler(client);
                players.add(player);
                // player est runnable et execute() execute son run
                pool.execute(player);
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
            player.sendMessage("Game is starting!");
        }
        // Implement the game logic and loop here
    }

    private void broadcastMovement(String movement) {
        for (ClientHandler player : players) {
            player.sendMessage(movement);
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket client;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket clientSocket) {
            this.client = clientSocket;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(client.getOutputStream(), true); // true permet d'envoyer directement sans attendre le remplissage du buffer
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received movement: " + message);
                    broadcastMovement(message); // Broadcast the movement to all players
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

        public void sendMessage(String message) {
            if (out != null) {
                out.println(message);
            }
        }
    }
}