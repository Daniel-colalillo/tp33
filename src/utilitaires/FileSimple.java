package utilitaires;

/**
 * File d'Object implémentée par liste simplement chaînée
 * @author simon
 *
 */
public class FileSimple {
	private Noeud tete;
	private int nbElements;
	
	
	/***
	 *  Classe interne décrivant un noeud de liste
	 * @author simon
	 *
	 */
	class Noeud {
		private Object element;
		private Noeud suivant;
		
		/***
		 * Constructeur pour la classe interne
		 * 
		 * @param element 	Object à encapsuler dans le Noeud
		 */
		public Noeud(Object element) {
			this.element = element;
			this.suivant = null;
		}
	}
	
	
	/***
	 * Constructeur par défaut pour la FileSimple
	 */
	public FileSimple() {
		tete = null;
		nbElements = 0;
	}
	
	
	/***
	 * La file est-elle vide
	 * 
	 * @return 	false si la file contient au moins 1 élément, true sinon
	 */
	public boolean estVide() {
		return nbElements == 0;
	}
	
	
	/***
	 * Ajoute un élément à la fin de la file
	 * 
	 * @param element 	Un objet à ajouter
	 */
	public void ajouterElement(Object element) {
		Noeud nouveau = new Noeud(element);
		
		if (this.estVide()) {
			tete = nouveau;
		} else {
			Noeud courant = tete;
			for (int i = 0; i < nbElements - 1; i++) {
				courant = courant.suivant;
			}
			courant.suivant = nouveau;
		}
		nbElements++;
	}
	
	
	/***
	 * Enleve un élément au début de la file et le retourne
	 * 
	 * @return 	L'Object retiré, null si la file est vide
	 */
	public Object enleverElement() {
		Object element;
		
		if(! this.estVide()) {
			element = tete.element;
		} else {
			element = null;
		}
		
		tete = tete.suivant;
		nbElements--;
		return element;
	}
	
}
