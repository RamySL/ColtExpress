package network;

import java.io.Serial;
import java.io.Serializable;

/**
 * classe qui représente un paquet qui sera transmis dans le réseau entre le serveur et les clients
 */
public abstract class Paquet implements Serializable {
    @Serial
    private static final long serialVersionUID  = 1L;
}
