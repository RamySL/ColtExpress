package network.Paquets;
import java.io.Serial;
/**
 * Le paquet qui sera envoyé aù hôte quand tous les joueurs se seront connectés
 * pour qu'il passe aux choix des paramètres de la partie
 */
public class PaquetChoixJrHost extends Paquet{
    @Serial
    private static final long serialVersionUID  = 13L;
}
