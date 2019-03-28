package modele.communication;

/**
 * Classe qui gère les comptes uniques. 
 * 
 * NOTE: thread safe
 * 
 * services offerts:
 * 	- CompteurMessage
 * 	- getCompteActuel
 * 
 * @author Frederic Simard, ETS
 * @author Simon Pichette, ETS (révision groupe 03)
 * @version Hiver, 2019
 */

import java.util.concurrent.locks.ReentrantLock;

public class CompteurMessage {

	private ReentrantLock lock = new ReentrantLock();
	
	private int compte = 0;
	
	/**
	 * Constructeur par défaut
	 */
	public CompteurMessage() {}
		
	/**
	 * Méthode permettant d'obtenir un compte incrémental unique.
	 * @return compte unique
	 */
	public int getCompteActuel() {
		
		int res = compte;
		lock.lock();
		compte++;
		lock.unlock();
		return res;
	}
	
}
