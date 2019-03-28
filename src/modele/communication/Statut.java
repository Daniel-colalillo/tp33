package modele.communication;

import utilitaires.Vecteur2D;

/**
 * Le Statut est un message spécialisé permettant de transmettre une position.
 * 
 * @author simon
 *
 */
public class Statut extends Message {
	Vecteur2D position;
	
	
	public Statut(int compte, Vecteur2D pos) {
		super(compte);
		position = pos;
	}

	/** Accesseurs **/
	public Vecteur2D getPosition() {
		return position;
	}
}
