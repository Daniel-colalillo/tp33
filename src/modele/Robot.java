package modele;
import java.util.Arrays;

/**
 * Classe simulant le robot d'exploration planétaire.
 * 
 * @author Frederic Simard, ETS
 * @author Simon Pichette, ETS (Révision groupe 03)
 * @version Hiver, 2019
 */
import modele.communication.Commande;
import modele.communication.Message;
import modele.communication.MorceauImage;
import modele.communication.Statut;
import modele.communication.TransporteurMessage;
import utilitaires.Vecteur2D;

public class Robot extends TransporteurMessage {
	
	private static final int TAILLE_CHUNK = 256;
	
	private SatelliteRelais satellite;
	private Vecteur2D position;
	private final double VITESSE_ms = 0.5;
	private Lune lune;
	
	/**
	 * Constructeur par paramètres
	 * @param sat 	SatelliteRelais
	 * @param lune 	Lune
	 * @param pos 	Vecteur2D position initiale
	 */
	public Robot(SatelliteRelais sat, Lune lune, Vecteur2D pos) {
		super();
		satellite = sat;
		position = pos;
		this.lune = lune;
	}

	@Override
	protected void envoyerMessage(Message msg) {
		satellite.envoyerMessageVersControle(msg);
		messagesEnvoyes.add(msg);
	}

	@Override
	protected void gestionnaireMessage(Message msg) {
		if (msg instanceof Commande) {
			gestionnaireCommande((Commande) msg);
		}
		
	}
	
	/**
	 * Appelle le sous-programme approprié selon la commande reçue
	 * 
	 * @param com 	La commande à traiter
	 */
	private void gestionnaireCommande(Commande com) {
		switch (com.getCommande()) {
			case NULLE :
				break;
			case DEPLACER_ROBOT:
				deplacerRobot(com.getDestination());
				break;
			case PRENDRE_PHOTO:
				prendrePhoto();
				break;
		}
	}
	
	/**
	 * Déplace le robot vers la destination reçue.
	 * 
	 * @param destination
	 */
	private void deplacerRobot(Vecteur2D destination) {
		Vecteur2D deplacement = destination.difference(position);
		double distance = deplacement.getLongueur();
		double angle = deplacement.getAngle();
		double tempsRestant = distance / VITESSE_ms;
		
		envoyerMessage(new Statut(compteurMsg.getCompteActuel(), position));
		
		while (tempsRestant > 1.0) {
			position = position.somme(new Vecteur2D(Math.cos(angle)*VITESSE_ms, 
													Math.sin(angle)*VITESSE_ms));
			envoyerMessage(new Statut(compteurMsg.getCompteActuel(), position));
			tempsRestant--;
		}
		
		// fraction de seconde restante
		position = position.somme(new Vecteur2D(
									Math.cos(angle)*VITESSE_ms*tempsRestant, 
									Math.sin(angle)*VITESSE_ms*tempsRestant));
		envoyerMessage(new Statut(compteurMsg.getCompteActuel(), position));
	}
	
	
	/**
	 * Méthode permettant de prendre une photo de la surface lunaire.
	 * La photo est pris par morceaux qui sont envoyés successivement 
	 * vers le centre de contrôle.
	 */
	private void prendrePhoto() {
		
		// tableau d'octets pour morceau d'image (chunk)
		byte[] chunk = new byte[TAILLE_CHUNK];
		
		try {
			// ouvre un fichier de photo
			long tailleTotale = lune.ouvrirFichierPhoto(position);
			
			// envoi tous les chunks jusqu'au dernier
			while(lune.lireChunkPhoto(chunk) != -1) {
				MorceauImage morceau = new MorceauImage(compteurMsg.getCompteActuel(),
								Arrays.copyOf(chunk, TAILLE_CHUNK), tailleTotale);
				envoyerMessage(morceau);
			}
			
			// envoi le message indiquant la fin de la photo
			MorceauImage dernier = new MorceauImage(compteurMsg.getCompteActuel(),true);
			envoyerMessage(dernier);
		}
		catch(Exception e) {
			System.out.println("echec prise image");
			System.out.println(e.getMessage());
		}
	}
}
