package modele;
/**
 * Classe simulant la Lune.
 * 
 * Cette classe répond aux demandes du robot concernant l'information sur la Lune.
 * Présentement, il est possible de prendre des photos de la surface lunaire, la photo
 * prise dépend de la position du Rover.
 * 
 * Les photos doivent être lues par morceaux (chunks).
 * 
 * Services offerts:
 *  - getInstance
 *  - ouvrirFichierPhoto
 *  - lireMorceauPhoto
 * 
 * @author Frederic Simard, ETS
 * @author Simon Pichette, ETS (Révision Groupe 03)
 * @version Hiver, 2019
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import utilitaires.Vecteur2D;

public class Lune {

	// constantes (privées si elles n'ont pas besoin d'être utilisées à l'extérieur de la classe)
	public static final Vecteur2D DIM_SITE = new Vecteur2D(200,200);
	private static final int NB_CRATERES = 20;
	private static final int DIVISION_GRILLE = 4;
	private static final String[][] IMAGES_SURFACE= {
		{"lunar-surface-1.jpg","lunar-surface-2.jpg","lunar-surface-3.jpg","lunar-surface-4.jpg"},
		{"lunar-surface-5.jpg","lunar-surface-6.jpg","lunar-surface-7.jpg","lunar-surface-8.jpg"},		 			
		{"lunar-surface-9.jpg","lunar-surface-8.jpg","lunar-surface-11.jpg","lunar-surface-12.jpg"},
		{"lunar-surface-13.jpg","lunar-surface-14.jpg","lunar-surface-15.jpg","lunar-surface-16.jpg"}};
	
	// donnees membres
	private ArrayList<Cratere> crateres; 
	private File fichierPhoto;
	private FileInputStream streamEntree; 
	
	/**
	 * Constructeur par défaut
	 */
	public Lune() {
		crateres = new ArrayList<Cratere>();
		fichierPhoto = null;
		streamEntree = null;
		
		// remplit la liste de cratères
		for(int i = 0; i <NB_CRATERES ;i++) {
			
			// ajoute un cratère avec position aléatoire dans la liste
			crateres.add(new Cratere(obtenirPositionAlea()));
		}
	}
	
	/**
	 * Méthode permettant d'ouvrir un fichier photo, la photo prise
	 * dépend de la position du Rover.
	 * @param position, position du Rover
	 * @throws Exception, exception lancée si l'on tente de prendre
	 * 					  une nouvelle photo alors que la précédente n'est
	 * 					  pas complétement transférée.
	 */
	public long ouvrirFichierPhoto(Vecteur2D position) throws Exception{
		
		// s'assure qu'aucune photo n'est déjà ouverte
		if(fichierPhoto != null) {
			throw new Exception("fichier photos déjà ouvert");
		}
		
		// calcule la position du robot dans la grille
		int posXGrille = (int)Math.floor((position.getX())/(DIM_SITE.getX()/DIVISION_GRILLE));
		int posYGrille = (int)Math.floor((position.getY())/(DIM_SITE.getY()/DIVISION_GRILLE));		
		
		// ouvre la photo correspondante
		fichierPhoto  = new File("images/" + IMAGES_SURFACE[posYGrille][posXGrille]);		
		streamEntree = new FileInputStream(fichierPhoto);
		return fichierPhoto.length();
	}

	/**
	 * méthode permettant de lire un morceau (chunk) de la photo
	 * @param chunk (sortie), tableau d'octets, contenant du chunk
	 * @return longeur du morceau lu,  -1 si photo terminée
	 * @throws Exception, exception lancée si aucune photo en cours
	 */
	public int lireChunkPhoto(byte[] chunk) throws Exception{
		int chunkLength = 0;

		// s'assure qu'une photo est ouverte
		if(fichierPhoto == null) {
			throw new Exception("aucun fichier de photo ouvert");
		}
		
		try {
			// lit un chunk de la photo
			chunkLength = streamEntree.read(chunk);
			
			// vérifie si la photo est terminé
			if(chunkLength == -1) {
				streamEntree.close();
				fichierPhoto = null;
			}
		} catch (IOException e1) {
			System.out.println("échec lecture photo");
		}
		return chunkLength;
	}	

	/**
	 * Méthode permettant d'obtenir une position aléatoire sur la lune
	 * @return Vecteur2D position aléatoire sur la lune
	 */
	public Vecteur2D obtenirPositionAlea(){
		return new Vecteur2D(Math.random()*DIM_SITE.getX(), 
						     Math.random()*DIM_SITE.getY());
	}
	
	/**
	 * Permet d'obtenir la liste de cratères
	 * @return ArrayList<Cratere> liste de cratères
	 */
	public ArrayList<Cratere> getCrateres() {
		return crateres;
	}
}
