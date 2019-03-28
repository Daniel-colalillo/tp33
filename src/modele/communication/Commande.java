package modele.communication;

import utilitaires.Vecteur2D;

/**
 * La commande est un message spécialisé qui contient un code de commande et
 * un vecteur de position.
 * 
 * @author simon
 *
 */
public class Commande extends Message {
	
	private eCommande commande;
	private Vecteur2D destination;
	
	/**
	 * Constructeur par paramètre
	 * @param compte 	Numéro de séquence unique
	 * @param com 		type de commande
	 * @param dest 		vecteur vers destination
	 */
	public Commande(int compte, eCommande com, Vecteur2D dest) {
		super(compte);
		commande = com;
		destination = dest;
	}
	
	/** Accesseurs **/
	public eCommande getCommande() {
		return commande;
	}
	
	public Vecteur2D getDestination() {
		return destination;
	}
}
