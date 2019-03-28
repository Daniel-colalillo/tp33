package modele.communication;

/**
 * Classe qui spécialise un Message.
 * 
 * Cette classe contient un morceau d'une image envoyé par le Rover.
 * Elle peut également indiquer la fin de l'image.
 * 
 * Services offerts:
 *  - MorceauImage 2x
 *  - getMorceau
 *  - getFin
 * 
 * @author Frederic Simard, ETS
 * @version Hiver, 2019
 */

public class MorceauImage extends Message {

	byte[] morceau;
	long tailleTotale;
	boolean fin = false;
	
	/**
	 * Constructeur permettant d'attacher un morceau d'image
	 * @param compte, compte unique du message
	 * @param morceau, morceau d'image
	 */
	public MorceauImage(int compte, byte[] morceau, long tailleTotale) {
		super(compte);
		this.morceau = morceau;
		this.tailleTotale = tailleTotale;
	}

	/**
	 * Constructeur permettant d'indique la fin d'une image
	 * @param compte, compte unique du message
	 * @param fin, true pour indiquer la fin d'une image
	 */
	public MorceauImage(int compte, boolean fin) {
		super(compte);
		this.fin = fin;
	}

	/**
	 * Accesseur pour obtenir le morceau d'image
	 * @return byte[], morceau d'image
	 */
	public byte[] getMorceau() {
		return morceau;
	}

	/**
	 * Accesseur pour obtenir la valeur du champ fin
	 * @return boolean, true indique la fin de l'image
	 */
	public boolean estFin() {
		return fin;
	}

	public long getTailleTotale() {
		return tailleTotale;
	}
}
