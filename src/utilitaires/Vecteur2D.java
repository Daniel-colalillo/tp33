package utilitaires;

/***
 * Vecteur 2 dimensions
 * @author simon
 *
 */
public class Vecteur2D implements Comparable<Vecteur2D> {
	private double x;
	private double y;
	
	public static final double PRECISION = 0.000001;	// 1 ppm
	
	/***
	 * Constructeur par défaut
	 */
	public Vecteur2D() {
		x = 0;
		y = 0;
	}
	
	/***
	 * Constructeur par paramètres
	 * 
	 * @param x 	Longueur en x du vecteur
	 * @param y 	Longueur en y du vecteur
	 */
	public Vecteur2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/*** 
	 * Constructeur par copie
	 * 
	 * @param v 	Le Vecteur2D à copier
	 */
	public Vecteur2D(Vecteur2D v) {
		x = v.getX();
		y = v.getY();
	}
	
	/* Accesseurs */
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	/**
	 * Longueur du vecteur selon formule de Pythagore
	 */
	public double getLongueur() {
		return Math.sqrt(x*x + y*y);
	}
	
	/***
	 * Angle en radians
	 */
	public double getAngle() {
		return Math.atan2(y, x);
	}
	
	/***
	 * Crée le vecteur différence entre ce Vecteur2D et un autre
	 * 
	 * @param v 	Vecteur2D par rapport auquel on cherche la différence
	 */
	public Vecteur2D difference(Vecteur2D v) {
		return new Vecteur2D(x - v.getX(), y - v.getY());
	}
	
	/***
	 * Crée le vecteur somme entre ce Vecteur2D et un autre
	 * 
	 * @param v		Vecteur2D par rapport auquel on cherche la somme
	 */
	public Vecteur2D somme(Vecteur2D v) {
		return new Vecteur2D(v.getX() + x, v.getY() + y);
	}
	
	/***
	 * Divise le vecteur par un facteur reçu
	 * 
	 * @param a 	facteur de division des composants du vecteur
	 */
	public void diviser(double a) {
		x = x / a;
		y = y / a;
	}
	
	/***
	 * Représentation du vecteur en chaîne de caractère
	 */
	public String toString() {
		return String.format("[%.3f,%.3f]", x, y);
	}
	
	/***
	 * Ce Vecteur2D est-il égal à un autre Vecteur2D
	 * 
	 * @param v 	Le vecteur avec lequel on souhaite comparer	
	 * @return 	True si les vecteurs sont égaux, faux sinon
	 */
	public boolean equals(Vecteur2D v) {
		if (Math.abs(v.getX() - x) < PRECISION && 
			Math.abs(v.getY() - y) < PRECISION) {
			return true;
		}
		return false;
	}
	
	/***
	 * Compare à un Vecteur2D par la longueur pour établir la relation d'ordre
	 * 
	 * @param v 	Le Vecteur2D avec lequel on souhaite comparer
	 * @return 	-1 si ce Vecteur2D est plus petit, 0 si égal et 1 si plus grand
	 */
	public int compareTo(Vecteur2D v) {
		if (this.equals(v)) return 0;
		if (this.getLongueur() > v.getLongueur()) return 1;
		return -1;
	}
}

