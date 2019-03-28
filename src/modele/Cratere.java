package modele;

/**
 * Classe qui définit un cratère lunaire
 * 
 * Services offerts:
 *  - Constructeur par paramètres
 *  - Accesseur informateur rayon et position
 * 
 * @author Frederic Simard, ETS
 * @author Simon Pichette, ETS (Révision Groupe 03)
 * @version Hiver, 2019
 */
import utilitaires.Vecteur2D;

public class Cratere {

	private static final double MAX_RAYON = 10.0;
	private static final double MIN_RAYON = 1.0;
	
	private double rayon;
	private Vecteur2D position;
	
	/**
	 * Constructeur par paramètre
	 * @param position (Vecteur2D) position du cratère
	 */
	public Cratere (Vecteur2D position) {
		this.rayon = Math.random()*(MAX_RAYON-MIN_RAYON)+MIN_RAYON;
		this.position = new Vecteur2D(position);
	}
	
	/**
	 * Accesseur, position
	 * @return double rayon du cratère
	 */
	public double getRayon() {
		return rayon;
	}

	/**
	 * Accesseur, position
	 * @return Vecteur2D position du cratère
	 */
	public Vecteur2D getPosition() {
		return position;
	}
}
