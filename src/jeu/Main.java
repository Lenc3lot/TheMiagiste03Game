package jeu;

/**
 * Classe principale de lancement du jeu "The Miagiste 03".
 *
 * Cette classe initialise les composants principaux du jeu :
 * création du jeu, de l'interface graphique, du compteur de temps, et lancement du jeu.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class Main {

	/**
	 * Point d'entrée principal du programme.
	 *
	 * @param args Arguments de la ligne de commande (non utilisés dans ce projet).
	 */
	public static void main(String[] args) {
		Jeu jeu = new Jeu();
		GUI gui = new GUI(jeu);
		jeu.setGUI(gui);

		Compteur compteur = new Compteur(gui);
		Thread threadCompteur = new Thread(compteur);
		threadCompteur.start();

		compteur.setCurrentJeu(jeu);
		jeu.setCompteur(compteur, threadCompteur);
	}
}
