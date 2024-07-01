package network.client;

import Vue.Accueil;
import controleur.ControleurAccueilHost;
import controleur.ControleurAccueilClient;
import controleur.ControleurJeuOnLine;
import controleur.ControleurServerClient;
import modele.actions.Action;
import modele.personnages.Bandit;
import network.Paquets.PaquetsClients.*;
import network.Paquets.PaquetsServeur.*;
import network.Paquets.PaquetsServeur.PaquetAction;

import java.io.*;
import java.net.*;
import java.util.Queue;

public class Client {
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ControleurServerClient cntrlServerClient;
    private ControleurAccueilClient controleurAccueilClient;
    private ControleurAccueilHost controleurAccueilHost;
    private ControleurJeuOnLine controleurJeu;
    private PaquetListePersoClient paquetListePersoClient;
    private PaquetListePersoHost paquetListePersoHost;
    private PaquetInitialisationPartie paquetInitialisationPartie;
    private int indiceBandit, indiceBanditCourant;
    boolean host = false;

    public Client(String serverAddress, int serverPort, ControleurServerClient ctrlServerClient) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.cntrlServerClient = ctrlServerClient;
        this.controleurAccueilHost = this.cntrlServerClient.getControleurAccueil();
        this.controleurAccueilHost.setClient(this);
    }

    public void setControleurJeu(ControleurJeuOnLine controleurJeu){
        this.controleurJeu = controleurJeu;
    }

    public void start() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Create a thread to listen for messages from the server
            Thread listenerThread = new Thread(new ServerListener());
            listenerThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
//        } finally {
//            closeConnections();
//        }
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

    public void sendListePlanififcation(Queue<Action> actions) throws IOException {
        this.out.writeObject(new PaquetListePlanififcation(actions));
        this.out.flush();
        this.out.reset();
    }

    public void actionExecute() throws IOException {
//        System.out.println("Client : envoi de actionExecute");
        this.out.writeObject(new network.Paquets.PaquetsClients.PaquetAction());
    }

    public int getIndiceBandit() {
        return indiceBandit;
    }

    public int getIndiceBanditCourant() {
        return indiceBanditCourant;
    }


    private void closeConnections() {
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
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
                            Client.this.indiceBandit = p.getIndiceBandit();
                            Client.this.indiceBanditCourant = p.getIndiceBanditCourant();

                            if (!host) {
                                Client.this.controleurAccueilClient.lancerPartie(paquetListePersoClient,paquetInitialisationPartie ,paquetInitialisationPartie.getTrain());
                            } else {
                                Client.this.controleurAccueilHost.lancerPartie(paquetListePersoHost, paquetInitialisationPartie, paquetInitialisationPartie.getTrain());
                            }
                        }

                        case PaquetPlanification paquetPlanification -> {
                            Client.this.controleurJeu.setPlanPhase();
                            Client.this.controleurJeu.prochaineManche();
                        }

                        case PaquetAction paquetAction -> {
                            Client.this.controleurJeu.setActionPhase();
                        }

                        case PaquetNextPlanification paquetNextPlanification -> {
                            Client.this.controleurJeu.nextBandit(paquetNextPlanification.getIndice());
                        }

                        case PaquetNextAction paquetNextAction -> {
                            Client.this.controleurJeu.nextBandit(paquetNextAction.getIndice());
                        }

                        case PaquetTrain paquetTrain ->{
                            Client.this.controleurJeu.actualiserTrain(paquetTrain.getTrain());
                        }

                        case PaquetBanditsGagnant paquetBanditsGagnant -> {
                            Client.this.controleurJeu.setFinPartie();
                            Client.this.controleurJeu.versFinJeu(paquetBanditsGagnant.getBandits());
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
