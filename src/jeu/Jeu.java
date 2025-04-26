package jeu;

import netscape.javascript.JSObject;

import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Jeu {
	
    private GUI gui; 
	private Zone zoneCourante;
    private String PATH_TO_SAVES = "savedFiles\\";
    private Sauvegarde actualGameState;
    private Joueur actualPlayer;
    private Stack<Zone> historiqueZones;
    private Compteur compteur;
    private Thread threadCompteur;

    public Jeu() {
        creerCarte();
        gui = null;
        historiqueZones = new Stack<>();
        actualPlayer = new Joueur("newJoueur");
        actualGameState = new Sauvegarde();
    }

    public void setGUI( GUI g) {
        gui = g; afficherMessageDeBienvenue();
    }
    
    private void creerCarte() {
        //Exterieur
        Zone zamZam = new Zone("Zam Zam", "ZamZamAvecKebab.png");
        Zone parking = new Zone("Parking", "Parking.png");

        //RDC
        Zone hallEntree = new Zone("Hall d'entrée", "hallEntree.png");
        Zone couloirRdcEst = new Zone("couloir RDC Est", "CouloirEst.png");
        Zone couloirRdcOuest = new Zone("couloir RDC Ouest", "CouloirOuest.png");
        Zone bureauAdministration = new Zone("Bureau administration", "bureauAdAvecCertif.png");
        Zone salle001 = new Zone("Salle 0.01", "Salle001AvecMaison.png");
        //Etage 1
        Zone hallEtage1 = new Zone("hall 1er étage", "hallEtage1.png");
        Zone couloirEtage1Est = new Zone("couloir 1er étage Est", "CouloirEst.png");
        Zone couloirEtage1Ouest = new Zone("couloir 1er étage Ouest", "CouloirOuest.png");
        Zone salleDePause = new Zone("Salle de pause", "salleDePauseAvecChatGpt.png");
        Zone salle101 = new Zone("Salle 1.01", "Salle101AvecGuide.png");
        //Etage 2
        Zone hallEtage2 = new Zone("hall 2ème étage", "hallEtage2.png");
        Zone couloirEtage2Est = new Zone("couloir 2ème étage Est", "CouloirEst.png");
        Zone couloirEtage2Ouest = new Zone("couloir 2ème étage Ouest", "CouloirOuest.png");
        Zone salle218 = new Zone("Salle 2.18", "Salle218AvecScrumUsb.png");
        Zone salle204 = new Zone("Salle 2.04", "salle204AvecArticlesMicroOndes.png");
        Zone salle201 = new Zone("Salle 2.01", "salle201AvecArticle.png");

        //Etage 3
        Zone hallEtage3 = new Zone("hall 3ème étage", "hallEtage3.png");
        Zone bureauBde = new Zone("Bureau BDE", "bureauBdeCoffreFerme.png");


        //Parking
        parking.ajouteSortie(Sortie.NORD, zamZam);
        parking.ajouteSortie(Sortie.OUEST, hallEntree);

        //ZamZam
        zamZam.ajouteSortie(Sortie.SUD, parking);

        //hall
        hallEntree.ajouteSortie(Sortie.EST, couloirRdcEst);
        hallEntree.ajouteSortie(Sortie.OUEST, couloirRdcOuest);
        hallEntree.ajouteSortie(Sortie.SUD, parking);
        hallEntree.ajouteSortie(Sortie.HAUT, hallEtage1);

        //couloir Rdc Est
        couloirRdcEst.ajouteSortie(Sortie.OUEST, hallEntree);
        couloirRdcEst.ajouteSortie(Sortie.SUD,bureauAdministration);

        //bureau Administration
        bureauAdministration.ajouteSortie(Sortie.OUEST, couloirRdcEst);

        //couloir Rdc Ouest
        couloirRdcOuest.ajouteSortie(Sortie.EST, hallEntree);
        couloirRdcOuest.ajouteSortie(Sortie.SUD, salle001);

        //Salle 0.01
        salle001.ajouteSortie(Sortie.SUD, couloirRdcOuest);

        //hall étage 1
        hallEtage1.ajouteSortie(Sortie.OUEST,couloirEtage1Ouest);
        hallEtage1.ajouteSortie(Sortie.EST,couloirEtage1Est);
        hallEtage1.ajouteSortie(Sortie.HAUT, hallEtage2);
        hallEtage1.ajouteSortie(Sortie.BAS, hallEntree);

        //Couloir 1er étage Est
        couloirEtage1Est.ajouteSortie(Sortie.SUD, salleDePause);
        couloirEtage1Est.ajouteSortie(Sortie.OUEST, hallEtage1);

        //Salle de pause
        salleDePause.ajouteSortie(Sortie.SUD, couloirEtage1Est);


        //Couloir 1er étage ouest
        couloirEtage1Ouest.ajouteSortie(Sortie.EST, hallEtage1);
        couloirEtage1Ouest.ajouteSortie(Sortie.SUD, salle101);

        //Salle 1.01
        salle101.ajouteSortie(Sortie.SUD, couloirEtage1Ouest);

        //Hall étage 2
        hallEtage2.ajouteSortie(Sortie.EST, couloirEtage2Est);
        hallEtage2.ajouteSortie(Sortie.OUEST, couloirEtage2Ouest);
        hallEtage2.ajouteSortie(Sortie.HAUT, hallEtage3);
        hallEtage2.ajouteSortie(Sortie.BAS, hallEtage1);

        //Couloir 2ème étage est
        couloirEtage2Est.ajouteSortie(Sortie.OUEST, hallEtage2);
        couloirEtage2Est.ajouteSortie(Sortie.SUD, salle218);

        //Salle 2.18
        salle218.ajouteSortie(Sortie.SUD, couloirEtage2Est);

        //Couloir 2ème étage ouest
        couloirEtage2Ouest.ajouteSortie(Sortie.EST, hallEtage2);
        couloirEtage2Ouest.ajouteSortie(Sortie.NORD, salle204);
        couloirEtage2Ouest.ajouteSortie(Sortie.SUD, salle201);

        //Salle 2.04
        salle204.ajouteSortie(Sortie.SUD, couloirEtage2Ouest);

        //Salle 2.01
        salle201.ajouteSortie(Sortie.SUD, couloirEtage2Ouest);

        //Hall étage 3
        hallEtage3.ajouteSortie(Sortie.EST, bureauBde);
        hallEtage3.ajouteSortie(Sortie.BAS, hallEtage2);

        //Bureau BDE
        bureauBde.ajouteSortie(Sortie.OUEST, hallEtage3);


        zoneCourante = hallEntree;
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
            case "CONNEXION":
                if (!actualPlayer.isLogged()){
                    actualPlayer.setPseudo(parametre);
                    gui.afficher("Connecté en tant que : "+ parametre);
                }else{
                    gui.afficher("Vous êtes déja connecté !");
                };
                break;
            case "SAUVEGARDE":
                sauvegarderJeu();
                gui.afficher("Sauvegarde réussie !");
                break;
            case "CHARGER":
                if(!actualPlayer.isLogged()){
                    gui.afficher("Vous n'êtes pas connecté !");
                }else{
                    // TODO : charger les éléments de sauvegarde
                    gui.afficher("Partie chargée !");
                }
                break;
            default:
                gui.afficher("Commande inconnue");
                break;
        }
    }

    private static final Map<String, String> directionsMap = Map.of(
    	    "N", "NORD",
    	    "S", "SUD",
    	    "E", "EST",
    	    "O", "OUEST",
    	    "H", "HAUT",
    	    "B", "BAS"
    	);


    private void allerEn(String direction) {
        direction = directionValideOuNull(direction);

        if (direction == null) {
            gui.afficher("Où voulez-vous aller ? Précisez une direction (N, S, E, O).");
            return;
        }

        Zone nouvelleZone = zoneCourante.obtientSortie(direction);

        if (nouvelleZone == null) {
            gui.afficher("Il n'y a pas de sortie dans cette direction !");
            return;
        }

        historiqueZones.push(zoneCourante);
        zoneCourante = nouvelleZone;

        afficherLocalisation();
        gui.afficheImage(zoneCourante.nomImage());
    }

    private String directionValideOuNull(String direction) {
        if (direction == null || direction.trim().isEmpty()) return null;
        String directionClean = direction.trim().toUpperCase();
        return directionsMap.getOrDefault(directionClean, directionClean);
    }

    private void parlerA(String personnage) {
        // TODO: Implémenter la logique de dialogue avec un PNJ
    }

    private void prendreObjet(String nomObjet) {
        if (nomObjet == null || nomObjet.trim().isEmpty()) {
            gui.afficher("Que voulez-vous prendre ?");
            return;
        }
        
        Objet objet = zoneCourante.retirerObjet(nomObjet);
        
        if (objet == null) {
            gui.afficher("Il n'y a pas de '" + nomObjet + "' ici.");
            return;
        }

        actualPlayer.getInventaireJoueur().ajouterObjet(objet);
        
        gui.afficher("Vous avez pris : " + objet.getLabel());
    }
    
    private void afficherInventaire() {
        List<Objet> objets = actualPlayer.getInventaireJoueur().getObjets();
        
        gui.afficher("=== INVENTAIRE ===");
        if (objets.isEmpty()) {
            gui.afficher("Votre inventaire est vide.");
        } else {
            gui.afficher("Contenu de l'inventaire :");
            for (Objet obj : objets) {
                gui.afficher("- " + obj.getLabel());
            }
        }
        
        gui.afficher("==================");
        gui.afficher();
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
        sauvegarderJeu();
        threadCompteur.interrupt();
    	gui.afficher( "Au revoir...");
    	gui.enable( false);
        System.exit(0);
    }

    public void sauvegarderJeu(){
        actualGameState.setMember("playerPseudo",actualPlayer.getPseudo());
        actualGameState.setMember("zoneCourante",zoneCourante);
        actualGameState.setMember("inventaireJoueur",actualPlayer.getInventaireJoueur());
        actualGameState.setMember("historiqueZones",historiqueZones);
        actualGameState.setMember("acutalTimeLeft",compteur.getTimeLeft());
        actualGameState.writeSave(PATH_TO_SAVES);
    }

    public JSObject getActualGameState(){
        return this.actualGameState;
    }

    public void setZoneCourante(Zone zoneCourante){
        this.zoneCourante = zoneCourante;
    }

    public void setCompteur(Compteur actualCompteur, Thread threadCompteur){
        this.compteur = actualCompteur;
        this.threadCompteur = threadCompteur;
    }
}
