package modele.communication;

/**
 * Le Nack (negative-acknowledgement) est un message spécialisé qui indique la 
 * non-réception d'un message précédent. Il contient le numéro du message à 
 * retransmettre.
 * 
 * @author simon
 *
 */
public class Nack extends Message {

	public Nack(int compte) {
		super(compte);
	}
}
