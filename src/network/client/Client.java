package network.client;

import Vue.Accueil;
import controleur.ControleurAccueilHost;
import controleur.ControleurAccueilClient;
import controleur.ControleurServerClient;
import modele.personnages.Bandit;
import network.Paquets.PaquetsClients.*;
import network.Paquets.PaquetsServeur.*;

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
    private ControleurAccueilClient controleurAccueilClient;
    private ControleurAccueilHost controleurAccueilHost;
    private PaquetListePersoClient paquetListePersoClient;
    private PaquetListePersoHost paquetListePersoHost;
    private PaquetInitialisationPartie paquetInitialisationPartie;
    private Bandit bandit;
    private Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infosBanditCourant;


    private Object notifieurInitBandits = new Object();

    boolean host = false;

    public Client(String serverAddress, int serverPort, ControleurServerClient ctrlServerClient) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.cntrlServerClient = ctrlServerClient;
        this.controleurAccueilHost = this.cntrlServerClient.getControleurAccueil();
        this.controleurAccueilHost.setClient(this);
    }

    public void start() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            userInput = new BufferedReader(new InputStreamReader(System.in));

            // Create a thread to listen for messages from the server
            Thread listenerThread = new Thread(new ServerListener());
            listenerThread.start();

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

    /**
     * envoi au serveur le perso et le surnom
     */
    public void sendChoixPerso(Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation infos) throws IOException {
        if (host){
            this.out.writeObject(new PaquetLancementHost(infos));
        }else{
            this.out.writeObject(new PaquetLancementClient(infos));
        }

    }

    public void sendParamJeu(String nbBallesBandits,String nbWagons,String nbActions,String nbManches, Double nervositeMarshall) throws IOException {
        if (host){
            this.out.writeObject(new PaquetInitialisationPartie( nbBallesBandits, nbWagons, nbActions, nbManches,  nervositeMarshall));
        }

    }

    /**
     * quand la partie se lance chaque client demande au serveur l'objet (Bandit) qui lui correspond
     */
    public void requestBandit() throws IOException {
        this.out.writeObject(new PaquetRequestBandit());
    }

    public void setControleurAccueilClient(ControleurAccueilClient controleurAccueilClient) {
        this.controleurAccueilClient = controleurAccueilClient;
    }

    public Bandit getBandit() {
        return bandit;
    }

    public Accueil.OptionsJeu.SelectionPersonnages.JoueurInfoCreation getInfosBanditCourant() {
        return infosBanditCourant;
    }

    public Object getNotifieurInitBandits() {
        return notifieurInitBandits;
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

                    switch (serverMessage) {
                        case PaquetNbJoeurConnecte p -> cntrlServerClient.updateNbJoueurConnecte(p.getNbJoueurRestants());

                        case PaquetChoixJrClient paquetChoixJrClient -> {
                            // vue sans param
                            cntrlServerClient.setControleurAccueilClient();
                            cntrlServerClient.vueClient();
                        }
                        case PaquetChoixJrHost paquetChoixJrHost -> {
                            host = true;
                            cntrlServerClient.vueHost();
                        }
                        //les prochains paquets vont arriver après que tout le monde ait appuyé sur lancer Partie
                        case PaquetListePersoClient listePersoClient -> Client.this.paquetListePersoClient = listePersoClient;

                        case PaquetListePersoHost listePersoHost -> Client.this.paquetListePersoHost = listePersoHost;

                        case PaquetInitialisationPartie paquetInitialisationPartie -> {
                            Client.this.paquetInitialisationPartie = paquetInitialisationPartie;
                            Client.this.requestBandit();
                        }

                        case PaquetBandit p -> {
                            if (!host) {
                                Client.this.controleurAccueilClient.lancerPartie(paquetListePersoClient,paquetInitialisationPartie ,paquetInitialisationPartie.getTrain());
                            } else {
                                Client.this.controleurAccueilHost.lancerPartie(paquetListePersoHost, paquetInitialisationPartie, paquetInitialisationPartie.getTrain());
                            }

                            Client.this.bandit = p.getBandit();
                            Client.this.infosBanditCourant = p.getInfosBanditCourant();
                            System.out.println("Bandit reçu");


                        }
                        case null, default -> {
                        }
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
