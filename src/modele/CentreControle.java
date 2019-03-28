package modele;
/**
 * Simulation du centre de contrôle terrestre qui communique avec le robot
 * par l'entremise du satellite relais.
 * 
 * @author Simon Pichette, ETS
 * @version Hiver, 2019
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import modele.communication.Commande;
import modele.communication.Message;
import modele.communication.MorceauImage;
import modele.communication.Statut;
import modele.communication.TransporteurMessage;
import modele.communication.eCommande;
import utilitaires.Vecteur2D;

public class CentreControle extends TransporteurMessage {
	
	// données membres
	private SatelliteRelais satellite;
	private File fichierPhoto;
	private FileOutputStream streamSortie;
	private int compteurPhoto;
	
    /**
     * Constructeur par paramètre
     * @param sat 	Référence vers le SatelliteRelais
     */
	public CentreControle(SatelliteRelais sat) {
		super();
		satellite = sat;
		fichierPhoto = null;
		streamSortie = null;
		compteurPhoto = 0;
	}

	@Override
	protected void envoyerMessage(Message msg) {
		satellite.envoyerMessageVersRobot(msg);
		messagesEnvoyes.add(msg);
	}
	
	/**
	 * Ordonne au robot de se déplacer à la position indiquée
	 * @param destination
	 */
	public void commandeDeplacer(Vecteur2D destination) {
		envoyerMessage(new Commande(compteurMsg.getCompteActuel(), 
				eCommande.DEPLACER_ROBOT, destination));
	}
	
	/**
	 * Ordonne au robot de prendre une photo sans changer de position
	 */
	public void commandePhoto() {
		envoyerMessage(new Commande(compteurMsg.getCompteActuel(), 
				eCommande.PRENDRE_PHOTO, null));
	}
	
	@Override
	protected void gestionnaireMessage(Message msg) {
		if (msg instanceof Statut) {
			// on traite le Message comme un Statut (typecast) (polymorphisme)
			traiterStatut((Statut)msg);
		} 
		
		if (msg instanceof MorceauImage) {
			// on traite le Message comme un MorceauImage (typecast) (polymorphisme)
			traiterMorceauImage((MorceauImage)msg);
		}
	}
	
	/**
	 * Routine de traitement des messages de statut
	 * @param stat
	 */
	private void traiterStatut(Statut stat) {
		System.out.println("Statut reçu\n    position du robot : " + 
				stat.getPosition());
	}
	
	/**
	 * Routine de traitement des messages contenant un morceau d'image
	 * @param morceau
	 */
	private void traiterMorceauImage(MorceauImage morceau) {
		// code adapté de la solution de Frédéric Simard
		try {
			// s'il ne s'agit pas de la fin
			if(morceau.estFin() == false) {
				
				// si aucun fichier n'est ouvert
				if(fichierPhoto == null) {
					System.out.println("Reception d'une photo, début");
					
					// on ouvre un fichier
					fichierPhoto = new File("photos/image" + compteurPhoto + ".jpg");
				    streamSortie = new FileOutputStream(fichierPhoto);
				}
				// on écrit le morceau dans un fichier ouvert
				System.out.print("*");
				streamSortie.write(morceau.getMorceau());
			}
			else {
				// ferme le fichier
				streamSortie.close();
				fichierPhoto = null;
				compteurPhoto++;
				System.out.println("\nReception d'une photo, terminée");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
