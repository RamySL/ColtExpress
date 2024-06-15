package controleur;

import Vue.OnLineSettigs;
import client.Client;
import server.Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class ControleurServerClient implements ActionListener {
    private OnLineSettigs olSettings;

    public ControleurServerClient(OnLineSettigs olSettings){
        this.olSettings = olSettings;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.olSettings.getLunchButton()){
            new Thread(() -> {
                int port = 12345; // Port to listen on
                int maxPlayers = 4; // Number of players needed to start the game
                Server server = new Server(port, maxPlayers);
                server.start();
            }).start();
        }

        if (e.getSource() == this.olSettings.getJoinButton()){
            new Thread(() -> {
                String serverAddress = "192.168.0.10"; // Server address
                int serverPort = 12345; // Server port
                Client client = new Client(serverAddress, serverPort);
                client.start();
            }).start();
        }

    }

}
