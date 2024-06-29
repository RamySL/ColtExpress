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
import modele.actions.Action;
import modele.actions.Braquer;
import modele.actions.SeDeplacer;
import modele.actions.Tirer;
import modele.personnages.Bandit;
import modele.trainEtComposantes.Train;
import network.Paquets.Paquet;
import network.Paquets.PaquetsClients.*;
import network.Paquets.PaquetsClients.PaquetAction;
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
    private Map< Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation, ClientHandler> mapPersoInfoClient;
    private ArrayList<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation> creationsJoueur = new ArrayList<>(); // parrait comme une duplication de values du hashMap mais c pour assurer
                                                                                                                        // l'ordononcement chose que ne fait une hashMap
    private Map<ClientHandler,Bandit> mapClientBandit = new HashMap<>();
    private boolean lancerPartie = false; // quand le host appui sur lancer devient true
    private PaquetInitialisationPartie paquetInitialisationPartie;
    private static final Object notifieurInitialisationPartie = new Object();
    private Partie partie;

    public Server(int port, int maxPlayers) {
        this.port = port;
        this.maxPlayers = maxPlayers;
        this.players = new ArrayList<>();
        this.pool = Executors.newFixedThreadPool(maxPlayers);
        this.nbJoueurConnecte = 0;
        this.mapPersoInfoClient = new HashMap<>();
    }

    /**
     * il ya une correspendance entre la liste de bandit dans Train et celle de creation joueur
     * celui à l'indice i de l'un correspend à celui de l'indice i de l'autre
     */
    private void  setMapBanditClientHand (){
        int i = 0;
        for (Bandit bandit : this.paquetInitialisationPartie.getTrain().getBandits()){
            this.mapClientBandit.put( mapPersoInfoClient.get(this.creationsJoueur.get(i)), bandit);
            i++;
        }
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
            while ((!this.lancerPartie) || this.mapPersoInfoClient.size() < this.maxPlayers){

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
                     this.setMapBanditClientHand();
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

             this.partie =  new Partie();




        } ).start();

    }

    public int getNbJoueurConnecte() {
        return nbJoueurConnecte;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Map<Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation, ClientHandler> getMapPersoInfoClient() {
        return mapPersoInfoClient;
    }

    public Map<ClientHandler, Bandit> getMapClientBandit() {
        return mapClientBandit;
    }

    private void broadCastPaquet (Paquet p) throws IOException {
        for (ClientHandler player : Server.this.players){
            player.out.writeObject(p);
        }
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
                while ((paquetClient = in.readObject()) != null) { // le probleme vient peut etre de banditCourant de controleurJeu
                    switch (paquetClient) {
                        case PaquetLancementClient paquetLancementClient -> {
                            Server.this.mapPersoInfoClient.put(paquetLancementClient.getInfos(), this);
                            Server.this.creationsJoueur.add(paquetLancementClient.getInfos());
                        }
                        case PaquetLancementHost paquetLancementHost -> {
                            Server.this.lancerPartie = true;
                            Server.this.creationsJoueur.add(paquetLancementHost.getInfos());
                            Server.this.mapPersoInfoClient.put(paquetLancementHost.getInfos(), this);
                        }
                        case PaquetInitialisationPartie initialisationPartie -> {
//                        System.out.println("Le run de client handler tourne sur le thread " + Thread.currentThread());
                            synchronized (notifieurInitialisationPartie) {
                                Server.this.paquetInitialisationPartie = initialisationPartie;
                                notifieurInitialisationPartie.notifyAll();
                            }
                        }
                        case PaquetRequestBandit paquetRequestBandit -> {
                            this.out.writeObject(new PaquetBandit(Server.this.getMapClientBandit().get(this), Server.this.paquetInitialisationPartie.getTrain().getBandits().getFirst()));
                        }

                        case PaquetListePlanififcation paquetListePlanififcation -> {
                            System.out.println("Server : reçu liste de planif");
                            for (Action action : paquetListePlanififcation.getListeAction()){
                                Bandit bandit = Server.this.mapClientBandit.get(this);
                                bandit.ajouterAction(partie.getCopieAction(action,bandit));
                            }
                            if ((partie.indiceBanditCourant+1) < partie.nbBandits){
                                partie.indiceBanditCourant ++;
                                Server.this.broadCastPaquet(new PaquetNextPlanification(partie.indiceBanditCourant));
                            }else {
                                partie.indiceBanditCourant = 0;
                                Server.this.broadCastPaquet(new network.Paquets.PaquetsServeur.PaquetAction());
                            }

                        }

                        case PaquetAction paquetAction -> {
                            // !! deplacement du marshall
                            // La partie doit changer pour les clients
                            Action actionAExecuter =  Server.this.mapClientBandit.get(this).getActions().peek();
                            Server.this.mapClientBandit.get(this).executer();

                            partie.nbActionsExecute ++;
                            Server.this.broadCastPaquet(new PaquetExecuteActionServer(partie.indiceBanditCourant,actionAExecuter));

                            partie.indiceBanditCourant = partie.nbActionsExecute % partie.nbBandits;

                            if (partie.nbActionsExecute < partie.totaleActionsManche){
                                Server.this.broadCastPaquet(new PaquetNextAction(partie.indiceBanditCourant));
                            }else {
                                partie.manche ++;
                                // soit nouvelle planif soit fin du jeu
                                if (partie.manche < partie.nbManches){
                                    Server.this.broadCastPaquet(new PaquetPlanification());
                                }else {
                                    Server.this.broadCastPaquet(new PaquetBanditsGagnant(partie.getBanditsGagnant()));
                                }
                            }
                        }

                        case null, default -> {
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

    /**
     * on met tous ce qui concerne et se passe pendant le deroulement de la partie ici
     */
    private class Partie{
        int manche = 0;
        private final int nbManches = Integer.parseInt( Server.this.paquetInitialisationPartie.getNbManches());
        private ArrayList<ArrayList<Action>> actionsBandits = new ArrayList<>();

        private Train train = Server.this.paquetInitialisationPartie.getTrain();

        private final int nbBandits = train.getBandits().size();

        private final int totaleActionsManche = Integer.parseInt(Server.this.paquetInitialisationPartie.getNbActions())  *  nbBandits;

        private int indiceBanditCourant = 0;
        private int nbActionsExecute = 0;

        private ArrayList<Bandit> getBanditsGagnant(){
            ArrayList<Bandit> bandits = this.train.getBandits();
            int scoreMax = 0;
            ArrayList<Bandit>  banditsGagnant = new ArrayList<>();
            for (Bandit b : bandits){
                if (b.score() > scoreMax){
                    scoreMax = b.score();
                }
            }
            for (Bandit b : bandits){
                if (b.score() == scoreMax){
                    banditsGagnant.add(b);
                }
            }

            return banditsGagnant;
        }

        /**
         * les actions reçus de la part des client pointent pas vers les bandits que a le serveur (sérialisation)
         */
        public Action getCopieAction (Action action, Bandit bandit){
            Action output;
            switch(action){
                case SeDeplacer seDeplacer -> {
                    output =  new SeDeplacer(bandit,seDeplacer.getDirection());
                }

                case Tirer tirer -> {
                    output  = new Tirer(bandit,tirer.getDirection());
                }

                case Braquer braquer -> {
                    output = new Braquer(bandit);
                }

                case null,default ->{
                    output = null;
                }
            }
            return output;
        }


    }




}