package modele.communication;
/**
 * Classe qui implémente le protocole de communication entre le robot
 * et le Centre d'opération.
 * 
 * Il se base sur une interprétation libre du concept de Nack:
 * 	https://webrtcglossary.com/nack/
 *  
 * Les messages envoyés sont mémorisés. À l'aide du compte unique
 * le transporteur de message peut identifier les messages manquant
 * dans la séquence et demander le renvoi d'un message à l'aide du Nack.
 * 
 * Pour contourner la situation où le Nack lui-même est perdu, le Nack
 * est renvoyé periodiquement, tant que le Message manquant n'a pas été reçu.
 * 
 * C'est également cette classe qui gère les comptes uniques.
 * 
 * Les messages reçus sont mis en file pour être traités.
 * 
 * La gestion des messages reçus s'effectue comme une tâche s'exécutant 
 * indépendamment (Thread)
 * 
 * Quelques détails:
 *  - Le traitement du Nack a priorité sur tout autre message.
 *  - Un message NoOp est envoyé périodiquement pour s'assurer de maintenir
 *    une communication active et identifier les messages manquants en bout de 
 *    séquence.
 * 
 * Services offerts:
 *  - TransporteurMessage
 *  - receptionMessageDeSatellite
 *  - run
 * 
 * @author Frederic Simard, ETS
 * @author Simon Pichette, ETS (révision groupe 03)
 * @version Hiver, 2019
 */

import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

public abstract class TransporteurMessage extends Thread {
	
	// compteur de message
	protected CompteurMessage compteurMsg;
	
	// lock qui protège la liste de messages reçus
	private ReentrantLock lock = new ReentrantLock();
	
	private ArrayList<Message> messagesRecus;
	protected Vector<Message> messagesEnvoyes;
	
	/**
	 * Constructeur, initialise le compteur de messages unique
	 * et les listes.
	 */
	public TransporteurMessage() {
		compteurMsg = new CompteurMessage();		
		messagesRecus = new ArrayList<Message>();
		messagesEnvoyes = new Vector<Message>();
	}
	
	/**
	 * Méthode gérant les messages reçus du satellite. La gestion se limite
	 * à l'implémentation du Nack, les messages spécialisés sont envoyés
	 * aux classes dérivées
	 * 
	 * @param msg, message reçu
	 */
	public void receptionMessageDeSatellite(Message msg) {
		lock.lock();
		
		try {
			/*
			 * (6.3.3) Insérer votre code ici 
			 */
			if (msg instanceof Nack) {
				// Insère les Nack au début
				messagesRecus.add(0, msg);
			} 
			else {
				// Si notre message n'est pas un Nack et son numéro est plus grand
				// que le dernier dans la liste, ou si la liste est vide,
				// insérer à la fin (cas typique).
				int dernier = messagesRecus.size() -1;
				
				if (messagesRecus.isEmpty() || 
						msg.getCompte() > messagesRecus.get(dernier).getCompte()) {
					messagesRecus.add(msg);
				} else {
					// Insère les autres en ordre de numéro de séquence,
					// les Nack en premier.
					for(int i = 0; i < messagesRecus.size(); i++) {
						if (messagesRecus.get(i).getCompte() >= msg.getCompte() &&
								! (messagesRecus.get(i) instanceof Nack)) {
							messagesRecus.add(i, msg);
							break;
						}
					}
				}
			}
		} 
		finally {
			lock.unlock();
		}
	}

	@Override
	/**
	 * Tâche effectuant la gestion des messages reçus
	 */
	public void run() {
		int compteCourant = 0;
		
		while(true) {
			lock.lock();
			
			try {
				boolean nackEnvoye = false;
				/*
				 * (6.3.4) Insérer votre code ici 
				 */
				while (!messagesRecus.isEmpty() && !nackEnvoye) {
					Message prochain = messagesRecus.remove(0);
					int compteProchain = prochain.getCompte();
					
					if (prochain instanceof Nack) {
						repeterMessage(compteProchain);
					} else {
						if (compteProchain > compteCourant) {
							envoyerMessage(new Nack(compteCourant));
							nackEnvoye = true;
						} else {
							if (compteProchain < compteCourant) {
								// on retire le message sans traitement
							} else {
								gestionnaireMessage(prochain);
								compteCourant++;
							}
						}
					}
				}
				envoyerMessage(new Noop(compteurMsg.getCompteActuel()));
			} 
			finally {
				lock.unlock();
			}
			
			// pause, cycle de traitement de messages
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Envoyer un message après avoir éliminé tous les messages précédents.
	 * 
	 * @param compte 	Le numéro de séquence du message à envoyer
	 */
	public void repeterMessage(int compte) {	
		for (int i = 0; i < messagesEnvoyes.size(); i++) {
			// enlever tous les messages au compte inférieur
			if (messagesEnvoyes.get(i).getCompte() < compte) {
				messagesEnvoyes.remove(i);
			}
			
			if (messagesEnvoyes.get(i).getCompte() == compte) {
				// envoyer le message à répéter
				envoyerMessage(messagesEnvoyes.get(i));
				break;
			}
		}
	}

	/**
	 * méthode abstraite utilisée pour envoyer un message
	 * @param msg, le message à envoyer
	 */
	abstract protected void envoyerMessage(Message msg);

	/**
	 * méthode abstraite utilisée pour effectuer le traitement d'un message
	 * @param msg, le message à traiter
	 */
	abstract protected void gestionnaireMessage(Message msg);

	

}
