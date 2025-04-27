package jeu;
import java.util.ArrayList;
import java.util.List;

/**
 * Enumération représentant l'ensemble des commandes disponibles dans le jeu.
 * Chaque commande possède une abréviation et une description permettant
 * d'expliquer son utilisation au joueur.
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */

public enum Commande {

	/** Déplacer le joueur dans une direction spécifique (Nord, Sud, Est, Ouest, Haut, Bas). */
	ALLER("ALLER", "ALLER [direction] (N, S, E, O, H, B)"),
	/** Parler avec un personnage du jeu. */
	PARLER("PARLER", "PARLER [personnage]"),
	/** Prendre un objet présent dans la zone actuelle. */
	PRENDRE("PRENDRE", "PRENDRE [objet] ou PRENDRE (si un seul objet)"),
	/** Déposer un objet dans la zone actuelle. */
	DEPOSER("DEPOSER", "DEPOSER [objet]"),
	/** Afficher l'inventaire du joueur. */
	INVENTAIRE("INVENTAIRE", "INVENTAIRE (afficher l'inventaire)"),
	/** Inspecter un élément ou un objet dans la zone actuelle. */
	INSPECTER("INSPECTER", "INSPECTER ou INSPECTER [élément]"),
	/** Ouvrir un conteneur (ex: coffre). */
	OUVRIR("OUVRIR", "OUVRIR [conteneur] ou OUVRIR (pour ouvrir le coffre)"),
	/** Utiliser un objet de l'inventaire. */
	UTILISER("UTILISER", "UTILISER [objet]"),
	/** Revenir à la zone précédemment visitée. */
	RETOUR("RETOUR", "RETOUR (retourner à la zone précédente)"),
	/** Se téléporter vers une autre zone du jeu. */
	TELEPORTER("TELEPORTER", "TELEPORTER (se téléporter à une zone)"),
	/** Quitter le jeu. */
	QUITTER("QUITTER", "QUITTER (quitter le jeu)"),
	/** Se connecter avec un pseudo pour gérer les sauvegardes. */
	CONNEXION("CONNEXION","CONNEXION [PSEUDO]"),
	/** Sauvegarder l'état actuel de la partie. */
	SAUVEGARDER("SAUVEGARDER","SAUVEGARDER (sauvegarde la partie)"),
	/** Charger une partie sauvegardée. */
	CHARGER("CHARGER","CHARGER (nécessite d'être connecté)"),
	/** Afficher la liste des commandes disponibles. */
	AIDE("?", "? (aide)");

	/** Abréviation de la commande (mot-clé à utiliser). */
	private String abreviation;

	/** Description de l'utilisation de la commande. */
	private String description;

	/**
	 * Constructeur privé de l'énumération.
	 *
	 * @param c Abréviation de la commande.
	 * @param d Description détaillée de la commande.
	 */
	private Commande(String c, String d) { 
		abreviation = c;
		description = d; 
	}

	/**
	 * Retourne le nom de l'énumération sous forme de chaîne.
	 *
	 * @return Nom de la commande.
	 */
	@Override
	public String toString() { 
		return name();
	}


	/**
	 * Retourne une liste contenant les descriptions de toutes les commandes.
	 *
	 * @return Liste des descriptions.
	 */
	public static List<String> toutesLesDescriptions() { 
		ArrayList<String> resultat = new ArrayList<String>();
		for(Commande c : values()) {
			resultat.add(c.description + "\n");
		}
		return resultat;
	}

	/**
	 * Retourne une liste contenant les abréviations de toutes les commandes.
	 *
	 * @return Liste des abréviations.
	 */
	public static List<String> toutesLesAbreviations() { 
		ArrayList<String> resultat = new ArrayList<String>();
		for(Commande c : values()) {
			resultat.add(c.abreviation);
		}
		return resultat;
	}

	/**
	 * Retourne une liste contenant les noms de toutes les commandes.
	 *
	 * @return Liste des noms.
	 */
	public static List<String> tousLesNoms() { 
		ArrayList<String> resultat = new ArrayList<String>();
		for(Commande c : values()) {
			resultat.add(c.name());
		}
		return resultat;
	}

}
