package programme;

import java.util.Scanner;
import modele.CentreControle;
import modele.Lune;
import modele.Robot;
import modele.SatelliteRelais;
import utilitaires.Vecteur2D;

public class ProgrammePrincipal {

	/**
	 * Programme principal, instancie les éléments de la simulation,
	 * les lient entre eux, puis lance la séquence de test.
	 * @param args, pas utilisé
	 */
	public static void main(String[] args){
		Scanner clavier = new Scanner(System.in);
	
		Lune laLune = new Lune(); 
		SatelliteRelais satellite = new SatelliteRelais();
		Robot robbie = new Robot(satellite, laLune, new Vecteur2D(100,100));
		CentreControle centre = new CentreControle(satellite);
		satellite.lierControle(centre);
		satellite.lierRobot(robbie);
		
		satellite.start();
		centre.start();
		robbie.start();
		
		// menu de test
		System.out.println("Tests de système");
		System.out.println("================");
		System.out.println("1. Tester déplacement");
		System.out.println("2. Tester prise de photo");
		System.out.print("    votre choix : ");
		int choix = clavier.nextInt();
		System.out.println();
		
		switch (choix) {
		case 1:
			centre.commandeDeplacer(new Vecteur2D(105, 95));
			break;
		case 2:
			centre.commandePhoto();
			break;
		}
		
		clavier.close();
	}
}
