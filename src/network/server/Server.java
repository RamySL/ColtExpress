package network.server;

/**
 * Le serveur doit prendre tous les parametres du jeu (nb de manches, balles etc) et le nombre de joueur qu'il va y'avoir
 * Donc les clients sont en attente tant que le nombre de joueur conecté n'est pas atteint, les clients sont stockés dans une
 * liste, la communication avec chaque un des clients se fait par l'intermediaire d'un thread (multi threading)
 */

/**
 * - Quand tous les joueurs sont connectés, les clients vont etre transmit vers la page où ils choisissent leur personnage, et le hot
 * - va etre dirigié vers une page ou en plus du personnage il choisit les paramètres du jeu
 * - le jeu se lance quand le hot lance (un message d'attente est affiché pour les clients */

import network.PaquetChoixJrHost;
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
                Socket client = listener.accept();
                InetAddress clientIP = client.getInetAddress();

                this.nbJoueurConnecte ++;
                ClientHandler player;

                if (clientIP.equals("127.0.0.1")){
                    player = new Host(client);
                }else{
                    player = new ClientHandler(client);
                }

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

    /**
     * on redirige les clients vers la page ou il choisissent et nomme leur perso
     */
    private void startGame() throws IOException {
        // deux paquet (le hot et les clients ont des actios différentes)
        for (ClientHandler player : players) {
            player.choixPerso();

        }

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

    /**
     * permet aux serveur d'interagir avec les clients
     */
    private class ClientHandler implements Runnable {
        protected final Socket client;
        protected ObjectOutputStream out;
        protected ObjectInputStream in;

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

        public void choixPerso () throws IOException {
            if (out != null) {
                out.writeObject(new PaquetChoixJrClient());
            }
        }
    }

    /**
     * un client spécial celui qui a lancé le serveur
     */
    class Host extends ClientHandler {
        public Host(Socket s) throws IOException {
            super(s);
        }

        @Override
        public void choixPerso() throws IOException {
            if (out != null) {
                out.writeObject(new PaquetChoixJrHost());
            }
        }
    }
}