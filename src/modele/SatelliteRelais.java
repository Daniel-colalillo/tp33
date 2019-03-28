package modele;

/**
 * Classe simulant le satellite relais
 * 
 * Le satellite se contente de transférer les messages du robot vers le 
 * centre de contrôle et vice-versa.
 * 
 * Le satellite exécute des cycles à intervale de TEMPS_CYCLE_MS. Période à
 * laquelle tous les messages en attente sont transmis. Ceci est implémenté à
 * l'aide d'une tâche (Thread).
 * 
 * Le relais satellite simule également les interférences dans l'envoi des 
 * messages.
 * 
 * Services offerts:
 *  - lierControle
 *  - lierRobot
 *  - envoyerMessageVersControle
 *  - envoyerMessageVersRobot
 * 
 * @author Frederic Simard, ETS
 * @author Simon Pichette, ETS (révision groupe 03)
 * @version Hiver, 2019
 */

import java.util.concurrent.locks.ReentrantLock;

import modele.communication.Message;
import utilitaires.FileSimple;

public class SatelliteRelais extends Thread{
	
	static final int TEMPS_CYCLE_MS = 500;
	static final double PROBABILITE_PERTE_MESSAGE = 0.05;
	
	static FileSimple messagesVersRobot = new FileSimple();
	static FileSimple messagesVersControle = new FileSimple();
	
	ReentrantLock lock = new ReentrantLock();
	
	private Robot robot;
	private CentreControle controle;
	
	/**
	 * Enregistre une référence vers le robot
	 * @param rob
	 */
	public void lierRobot(Robot rob) {
		robot = rob;
	}
	
	/**
	 * Enregistre une référence vers le centre de contrôle
	 * @param centre
	 */
	public void lierControle(CentreControle centre) {
		controle = centre;
	}
	
	
	
	/**
	 * Méthode permettant d'envoyer un message vers le centre de contrôle
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersControle(Message msg) {
		
		lock.lock();
		
		try {

			/*
			 * (5.1) Insérer votre code ici 
			 */
			double perteMessage = Math.random();
			
			if (perteMessage > PROBABILITE_PERTE_MESSAGE) {
				messagesVersControle.ajouterElement(msg);
			}
			
		}finally {
			lock.unlock();
		}
	}
	
	/**
	 * Méthode permettant d'envoyer un message vers le robot
	 * @param msg, message à envoyer
	 */
	public void envoyerMessageVersRobot(Message msg) {
		lock.lock();
		
		try {

			/*
			 * (5.2) Insérer votre code ici 
			 */
			double perteMessage = Math.random();
			
			if (perteMessage > PROBABILITE_PERTE_MESSAGE) {
				messagesVersRobot.ajouterElement(msg);
			}
			
		}finally {
			lock.unlock();
		}
	}

	@Override
	public void run() {
		
		while(true) {
			
			/*
			 * (5.3) Insérer votre code ici 
			 */

			// faire suivre les messages à destination du robot
			while(!messagesVersRobot.estVide()) {
				robot.receptionMessageDeSatellite(
						(Message)messagesVersRobot.enleverElement());
			}
			
			// faire suivre les messages à destination du centre de contrôle
			while(!messagesVersControle.estVide()) {
				controle.receptionMessageDeSatellite(
						(Message)messagesVersControle.enleverElement());
			}
			
			// attend le prochain cycle
			try {
				Thread.sleep(TEMPS_CYCLE_MS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
