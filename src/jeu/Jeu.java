package jeu;

import netscape.javascript.JSObject;
import java.util.Stack;

public class Jeu {
	
    private GUI gui; 
	private Zone zoneCourante;
    private String PATH_TO_SAVES = "";
    private JSObject loadedGameSave;
    private Stack<Zone> historiqueZones; // Pour la commande RETOUR
    private Inventaire inventaire; // Pour gérer l'inventaire du joueur
    
    public Jeu() {
        creerCarte();
        gui = null;
        historiqueZones = new Stack<>();
        inventaire = new Inventaire();
    }

    public void setGUI( GUI g) { gui = g; afficherMessageDeBienvenue(); }
    
    private void creerCarte() {
//        Zone [] zones = new Zone [4];
//        zones[0] = new Zone("le couloir", "Couloir.jpg" );
//        zones[1] = new Zone("l'escalier", "Escalier.jpg" );
//        zones[2] = new Zone("la grande salle", "GrandeSalle.jpg" );
//        zones[3] = new Zone("la salle à manger", "SalleAManger.jpg" );
//        zones[0].ajouteSortie(Sortie.EST, zones[1]);
//        zones[1].ajouteSortie(Sortie.OUEST, zones[0]);
//        zones[1].ajouteSortie(Sortie.EST, zones[2]);
//        zones[2].ajouteSortie(Sortie.OUEST, zones[1]);
//        zones[3].ajouteSortie(Sortie.NORD, zones[1]);
//        zones[1].ajouteSortie(Sortie.SUD, zones[3]);
//        zoneCourante = zones[1];
    }

    private void afficherLocalisation() {
            gui.afficher( zoneCourante.descriptionLongue());
            gui.afficher();
    }

    private void afficherMessageDeBienvenue() {
    	gui.afficher("Bienvenue !");
    	gui.afficher();
        gui.afficher("Tapez '?' pour obtenir de l'aide.");
        gui.afficher();
        afficherLocalisation();
        gui.afficheImage(zoneCourante.nomImage());
    }
    
    public void traiterCommande(String commandeLue) {
    	gui.afficher( "> "+ commandeLue + "\n");
        String[] parties = commandeLue.split(" ", 2);
        String commande = parties[0].toUpperCase();
        String parametre = parties.length > 1 ? parties[1].toUpperCase() : "";

        switch (commande) {
            case "ALLER":
                allerEn(parametre);
                break;
            case "PARLER":
                parlerA(parametre);
                break;
            case "PRENDRE":
                prendreObjet(parametre);
                break;
            case "DEPOSER":
                deposerObjet(parametre);
                break;
            case "INVENTAIRE":
                afficherInventaire();
                break;
            case "INSPECTER":
                inspecter(parametre);
                break;
            case "OUVRIR":
                ouvrirConteneur(parametre);
                break;
            case "UTILISER":
                utiliserObjet(parametre);
                break;
            case "RETOUR":
                retourner();
                break;
            case "TELEPORTER":
                teleporter();
                break;
            case "QUITTER":
                terminer();
                break;
            case "?":
            case "AIDE":
                afficherAide();
                break;
            default:
                gui.afficher("Commande inconnue");
                break;
        }
    }

    private void allerEn(String direction) {
        // TODO: Implémenter la logique de déplacement
        // Vérifier si la direction est valide (N, S, E, O, H, B)
        // Sauvegarder la zone actuelle dans l'historique
        // Changer de zone si possible
    }

    private void parlerA(String personnage) {
        // TODO: Implémenter la logique de dialogue avec un PNJ
    }

    private void prendreObjet(String objet) {
        // TODO: Implémenter la logique de prise d'objet
        // Vérifier si l'objet existe dans la zone
        // Ajouter à l'inventaire
    }

    private void deposerObjet(String objet) {
        // TODO: Implémenter la logique de dépôt d'objet
        // Vérifier si l'objet est dans l'inventaire
        // Retirer de l'inventaire et ajouter à la zone
    }

    private void afficherInventaire() {
        // TODO: Implémenter l'affichage de l'inventaire
        gui.afficher("Inventaire :");
        // Afficher les objets de l'inventaire
    }

    private void inspecter(String element) {
        // TODO: Implémenter la logique d'inspection
        // Afficher la description détaillée de l'élément
    }

    private void ouvrirConteneur(String conteneur) {
        // TODO: Implémenter la logique d'ouverture de conteneur
    }

    private void utiliserObjet(String objet) {
        // TODO: Implémenter la logique d'utilisation d'objet
    }

    private void retourner() {
        if (!historiqueZones.isEmpty()) {
            zoneCourante = historiqueZones.pop();
            afficherLocalisation();
            gui.afficheImage(zoneCourante.nomImage());
        } else {
            gui.afficher("Aucune zone précédente");
        }
    }

    private void teleporter() {
        // TODO: Implémenter la logique de téléportation
        // Afficher la liste des zones disponibles
        // Permettre au joueur de choisir une zone
    }

    private void afficherAide() {
        gui.afficher("Etes-vous perdu ?");
        gui.afficher();
        gui.afficher("Les commandes autorisées sont :");
        gui.afficher();
        gui.afficher(Commande.toutesLesDescriptions().toString());
        gui.afficher();
    }
    
    private void terminer() {
    	gui.afficher( "Au revoir...");
    	gui.enable( false);
    }
}
