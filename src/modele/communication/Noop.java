package modele.communication;

/**
 * Le Noop (No-operation) est un message spécialisé permettant de garder un 
 * canal de communication ouvert lorsqu'aucun message n'est transmis.
 * 
 * @author simon
 *
 */
public class Noop extends Message {
	
	public Noop(int compte) {
		super(compte);
	}
}
