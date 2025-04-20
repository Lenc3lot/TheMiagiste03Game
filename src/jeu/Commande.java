package jeu;
import java.util.ArrayList;
import java.util.List;

public enum Commande {
	ALLER("ALLER", "ALLER [direction] (N, S, E, O, H, B)"),
	PARLER("PARLER", "PARLER [personnage]"),
	PRENDRE("PRENDRE", "PRENDRE [objet]"),
	DEPOSER("DEPOSER", "DEPOSER [objet]"),
	INVENTAIRE("INVENTAIRE", "INVENTAIRE (afficher l'inventaire)"),
	INSPECTER("INSPECTER", "INSPECTER [élément]"),
	OUVRIR("OUVRIR", "OUVRIR [conteneur]"),
	UTILISER("UTILISER", "UTILISER [objet]"),
	RETOUR("RETOUR", "RETOUR (retourner à la zone précédente)"),
	TELEPORTER("TELEPORTER", "TELEPORTER (se téléporter à une zone)"),
	QUITTER("QUITTER", "QUITTER (quitter le jeu)"),
	AIDE("?", "? (aide)");

	private String abreviation;
	private String description;
	private Commande(String c, String d) { 
		abreviation = c;
		description = d; 
	}
	@Override
	public String toString() { 
		return name();
	}
	
	public static List<String> toutesLesDescriptions() { 
		ArrayList<String> resultat = new ArrayList<String>();
		for(Commande c : values()) {
			resultat.add(c.description);
		}
		return resultat;
	}
	
	public static List<String> toutesLesAbreviations() { 
		ArrayList<String> resultat = new ArrayList<String>();
		for(Commande c : values()) {
			resultat.add(c.abreviation);
		}
		return resultat;
	}
	
	public static List<String> tousLesNoms() { 
		ArrayList<String> resultat = new ArrayList<String>();
		for(Commande c : values()) {
			resultat.add(c.name());
		}
		return resultat;
	}

}
