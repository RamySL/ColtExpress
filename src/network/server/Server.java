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

import Vue.Accueil;
import network.Paquets.PaquetsClients.*;
import network.Paquets.PaquetsServeur.*;

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
    private Map<ClientHandler, Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> mapClientPerso;
    private ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJoueur = new ArrayList<>(); // parrait comme une duplication de values du hashMap mais c pour assurer
                                                                                                                        // l'ordononcement chose que ne fait une hashMap
    private boolean lancerPartie = false; // quand le host appui sur lancer devient true
    private PaquetInitialisationPartie paquetInitialisationPartie;
    private static final Object notifieurInitialisationPartie = new Object();

    public Server(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(maxPlayers);
        this.nbJoueurConnecte = 0;
        this.mapClientPerso = new HashMap<>();
    }

    public void start() {
        // the 0 for backlog sets the limit to default
        // le 0.0.0.0 ip lie le serveur à tous les ip local de la machine
        try (ServerSocket listener = new ServerSocket(port, 0, InetAddress.getByName("0.0.0.0"))) {
            while (players.size() < maxPlayers) {
                Socket client = listener.accept();
                InetAddress clientIP = client.getInetAddress();

                this.nbJoueurConnecte ++;
                ClientHandler player;

                if (clientIP.getHostAddress().equals("127.0.0.1")){
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

        // nouveau thread pour la partie
         new Thread(() -> {
            while ((!this.lancerPartie) || this.mapClientPerso.size() < this.maxPlayers){

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

             synchronized (notifieurInitialisationPartie){
                 try {
                     while (this.paquetInitialisationPartie == null){
                         notifieurInitialisationPartie.wait();
                     }
                     this.paquetInitialisationPartie.initTrain(this.creationsJoueur);
                 } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                 }
             }

             for (ClientHandler player : players) {
                try {
                    player.sendPersoList(this.creationsJoueur);
                    player.sendInitPartie(this.paquetInitialisationPartie);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }


        } ).start();

    }
    public int getNbJoueurConnecte() {
        return nbJoueurConnecte;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Map<ClientHandler, Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> getMapClientPerso() {
        return mapClientPerso;
    }

    /**
     * permet aux serveur d'interagir avec les clients
     */
    protected class ClientHandler implements Runnable {
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
                Object paquetClient;
                while ((paquetClient = in.readObject()) != null) {
                    if (paquetClient instanceof PaquetLancementClient){
                        Server.this.mapClientPerso.put(this,((PaquetLancementClient) paquetClient).getInfos());
                        Server.this.creationsJoueur.add(((PaquetLancementClient) paquetClient).getInfos());
                    }else if (paquetClient instanceof PaquetLancementHost){
                        Server.this.lancerPartie = true;
                        Server.this.creationsJoueur.add(((PaquetLancementHost) paquetClient).getInfos());
                        Server.this.mapClientPerso.put(this,((PaquetLancementHost) paquetClient).getInfos());
                    }else if (paquetClient instanceof PaquetInitialisationPartie){
//                        System.out.println("Le run de client handler tourne sur le thread " + Thread.currentThread());
                        synchronized (notifieurInitialisationPartie){
                            Server.this.paquetInitialisationPartie = (PaquetInitialisationPartie) paquetClient;
                            notifieurInitialisationPartie.notifyAll();
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
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

        public void sendPersoList(ArrayList <Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> infosListe) throws IOException {
            out.writeObject(new PaquetListePersoClient(infosListe));
        }

        public void sendInitPartie(PaquetInitialisationPartie paquetInitialisationPartie) throws IOException {
            out.writeObject(paquetInitialisationPartie);
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

        @Override
        public void sendPersoList(ArrayList <Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> infosListe) throws IOException {
            out.writeObject(new PaquetListePersoHost(infosListe));
        }

    }


}