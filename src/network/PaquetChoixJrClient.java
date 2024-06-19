package network;

import network.Paquet;

import java.io.Serial;

/**
 *  LE paquet qui sera envoyé aux clients quand tous les joueurs se seront connectés
 */
public class PaquetChoixJrClient extends Paquet {
    @Serial
    private static final long serialVersionUID  = 12L;
}
