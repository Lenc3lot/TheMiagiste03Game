package jeu;

import jeu.PNJ.PNJ_Admin;
import jeu.PNJ.PNJ_Prof;
import jeu.PNJ.PNJ_ZamZam;
import jeu.PNJ.PNJ_Guide;
import netscape.javascript.JSObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Jeu implements Serializable {
	
    private GUI gui; 
	private Zone zoneCourante;
    private Sauvegarde actualGameState;
    private Joueur actualPlayer;
    private Stack<Zone> historiqueZones;
    private Compteur compteur;
    private Thread threadCompteur;
    private boolean isTimeOut = false;

    // CREATION DES OBJETS
    public static final Objet CERTIFICATION = new Objet("CERTIF", "Certification MIAGE", true, true,
            "Votre certification MIAGE qui sert aussi de TODO list");
    public static final Objet MAISON = new Objet("MAISON", "Maison de qualité", true, true,
            "Une représentation de la maison de la qualité en management");
    public static final Objet GUIDE_INTEGRATION = new Objet("GUIDE", "Guide d'intégration", true, true,
            "Guide pour l'intégration en transformation numérique");
    public static final Objet ARTICLE_BANGLADESH = new Objet("ARTICLE", "Article sur le Bangladesh", true, true,
            "Article géopolitique sur le Bangladesh");
    public static final Objet ON2 = new Objet("ON2", "O(n²)", true, true,
            "Représentation de la complexité algorithmique");
    public static final Objet SCRUM_BOOK = new Objet("SCRUM", "Scrum Book", true, true,
            "Livre sur la méthodologie Scrum");
    public static final Objet CLE_USB = new Objet("USB", "Clé USB Wooclap", true, true,
            "Clé USB contenant les présentations Wooclap");
    public static final Objet CHAT_GPT = new Objet("GPT", "Chat GPT", true, true,
            "Assistant d'intelligence artificielle");
    public static final Objet KEBAB = new Objet("KEBAB", "Kebab du ZAM ZAM", false, true,
            "Un délicieux kebab du ZAM ZAM");
    public static final Objet CAFE = new Objet("CAFE", "Café", false, true,
            "Un bon café pour se réveiller");

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

    public void sauvegarderJeu(){
        actualGameState.setMember("playerPseudo",actualPlayer.getPseudo());
        actualGameState.setMember("zoneCourante",zoneCourante);
        actualGameState.setMember("inventaireJoueur",actualPlayer.getInventaireJoueur());
        actualGameState.setMember("historiqueZones",historiqueZones);
        actualGameState.setMember("acutalTimeLeft",compteur.getTimeLeft());
        actualGameState.writeSave();
    }

    public void traiterCommande(String commandeLue) {
        if(!isTimeOut){
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
                        actualGameState.setMember("playerPseudo",parametre);
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
                        HashMap<String, Object> loadSave = actualGameState.loadSave();
                        compteur.setTimeBack(Integer.parseInt(loadSave.get("acutalTimeLeft").toString()));
                        setZoneCourante((Zone) loadSave.get("zoneCourante"));
                        afficherLocalisation();
                        gui.afficheImage(zoneCourante.nomImage());
                        historiqueZones = (Stack<Zone>) loadSave.get("historiqueZones");
                        actualPlayer.setInventaireJoueur((Inventaire) loadSave.get("inventaireJoueur"));
                        gui.afficher("Partie chargée !");
                    }
                    break;
                default:
                    gui.afficher("Commande inconnue");
                    break;
            }
        } else {
            gui.afficher("Le temps est écoulé ! Vous pouvez recommencer en relancant une partie !");
            gui.afficher();
        }
    }

    public void timeIsOut() {
        gui.afficher("Le temps est écoulé ! Vous n'avez pas réussi à obtenir votre diplôme à temps...");
        gui.afficher();
        isTimeOut = true;
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
        String message = actualPlayer.parler(personnage, zoneCourante);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }

    private void prendreObjet(String nomObjet) {
        String message = actualPlayer.prendreObjet(nomObjet, zoneCourante);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }
    
    private void afficherInventaire() {
        String message = actualPlayer.afficherInventaire();
        gui.afficher(message);
    }

    private void inspecter(String element) {
        String message = actualPlayer.inspecter(element, zoneCourante);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }

    private void ouvrirConteneur(String conteneur) {
        String message = actualPlayer.ouvrir(conteneur, zoneCourante);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }

    private void utiliserObjet(String objet) {
        // TODO: Implémenter la logique d'utilisation d'objet
    }

    private void creerCarte() {
        //Exterieur
        Zone zamZam = new Zone("Zam Zam", "ZamZamAvecKebab.png", "ZamZamSansKebab.png");
        Zone parking = new Zone("Parking", "Parking.png");

        //RDC
        Zone hallEntree = new Zone("Hall d'entrée", "hallEntree.png");
        Zone couloirRdcEst = new Zone("couloir RDC Est", "CouloirEst.png");
        Zone couloirRdcOuest = new Zone("couloir RDC Ouest", "CouloirOuest.png");
        Zone bureauAdministration = new Zone("Bureau administration", "bureauAdAvecCertif.png", "bureauAdSansCertif.png");
        Zone salle001 = new Zone("Salle 0.01", "Salle001AvecMaison.png", "Salle001SansMaison.png");

        //Etage 1
        Zone hallEtage1 = new Zone("hall 1er étage", "hallEtage1.png");
        Zone couloirEtage1Est = new Zone("couloir 1er étage Est", "CouloirEst.png");
        Zone couloirEtage1Ouest = new Zone("couloir 1er étage Ouest", "CouloirOuest.png");
        Zone salleDePause = new Zone("Salle de pause", "salleDePauseAvecChatGpt.png", "salleDePauseSansChatGpt.png");
        Zone salle101 = new Zone("Salle 1.01", "Salle101AvecGuide.png", "Salle101SansGuide.png");

        //Etage 2
        Zone hallEtage2 = new Zone("hall 2ème étage", "hallEtage2.png");
        Zone couloirEtage2Est = new Zone("couloir 2ème étage Est", "CouloirEst.png");
        Zone couloirEtage2Ouest = new Zone("couloir 2ème étage Ouest", "CouloirOuest.png");
        Zone salle218 = new Zone("Salle 2.18", "Salle218AvecScrumUsb.png", "Salle218SansScrumUsb.png");
        Zone salle204 = new Zone("Salle 2.04", "salle204AvecArticlesMicroOndes.png", "salle204SansArticlesMicroOndes.png");
        Zone salle201 = new Zone("Salle 2.01", "salle201AvecArticle.png", "salle201SansArticle.png");

        //Etage 3
        Zone hallEtage3 = new Zone("hall 3ème étage", "hallEtage3.png");
        Zone bureauBde = new Zone("Bureau BDE", "bureauBdeCoffreFerme.png", "bureauBdeCoffreOuvert.png");

        // Ajout questions & création des PNJ
        PNJ_Guide sin = new PNJ_Guide("SIN",
                "SIN",
                new String[]{"Bonjour !",
                        "Comment puis-je vous aider ?"});

        PNJ_Admin sandrine = new PNJ_Admin("SAND",
                "Sandrine",
                new String[]{"Bonjour !",
                        "Je peux vous aider ?"});

        PNJ_ZamZam chefZamZam = new PNJ_ZamZam("ZAM",
                "Chef du ZAM ZAM",
                new String[]{"Bienvenue au ZAM ZAM !",
                        "Que puis-je vous servir ?"});

        // Ajout des professeurs avec leurs questions
        PNJ_Prof profTransfo = getPnjProfTransfo();
        PNJ_Prof profQualite = getPnjProfQualite();
        PNJ_Prof profAnglais = getPnjProfAnglais();
        PNJ_Prof profAlgo = getPnjProfAlgo();
        PNJ_Prof profGestion = getPnjProfGestion();

        PNJ_Prof profWooclip = getPnjProfWooclip();

        // Ajout des PNJ aux zones
        hallEntree.setPNJ(sin);
        bureauAdministration.setPNJ(sandrine);
        zamZam.setPNJ(chefZamZam);
        salle001.setPNJ(profQualite);
        salle101.setPNJ(profTransfo);
        salle201.setPNJ(profAnglais);
        salle204.setPNJ(profAlgo);
        salle218.setPNJ(profGestion);
        salle218.setPNJ(profWooclip);

        // Ajout des objets dans les zones
        hallEntree.ajouterObjet(CAFE);
        bureauAdministration.ajouterObjet(CERTIFICATION);
        salleDePause.ajouterObjet(CHAT_GPT);
        zamZam.ajouterObjet(KEBAB);

        //## AJOUT DES DIFFERENTES SORTIES##
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

    // GENERATE ALL PROF
    private PNJ_Prof getPnjProfTransfo() {
        PNJ_Prof profTransfo = new PNJ_Prof("PROF_TRANSFO",
                "1 -Professeur de Transformation",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                GUIDE_INTEGRATION,
                60,
                "Transformation numérique");

        Question question1Transfo = new Question(1,
                "Dans quel évènement historique Napoléon a perdu car il n'a pas intégré les acteurs ? \n" +
                        "A- Bataille de Waterloo \n" +
                        "B- La révolution francaise \n" +
                        "C- Pendant la guerre des étoiles \n",
                "A");

        Question question2Transfo = new Question(2,
                "Comment éviter les résistances aux changements ? \n" +
                        "A- Il faut intégrer les acteurs \n" +
                        "B- Il faut les menacer \n" +
                        "C- La réponse C \n",
                "A");

        Question question3Transfo = new Question(3,
                "Comment faire évoluer une entreprise ? \n" +
                        "A- En faisant de la transformation numérique \n" +
                        "B- En agrandissanr les locaux \n" +
                        "C- En faisant gagner un niveau sur la route 3 \n",
                "A");
        profTransfo.ajouterQuestion(question1Transfo);
        profTransfo.ajouterQuestion(question2Transfo);
        profTransfo.ajouterQuestion(question3Transfo);
        return profTransfo;
    }
    private PNJ_Prof getPnjProfQualite(){
        PNJ_Prof profQualite = new PNJ_Prof("PROF_QUAL",
                "1 -Professeur de Qualité",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                MAISON,
                60,
                "Management de la qualité"
        );
        Question question1Qualite = new Question(1,
                "La gestion de la qualité c'est... ? \n" +
                        "A- Bien. \n" +
                        "B- Etre capable de creer de la VA dans son entreprise \n" +
                        "C- Savoir faire un truc bien. \n",
                "B");

        Question question2Qualite = new Question(2,
                "Comment optimiser une chaine de production ? \n" +
                        "A- En localisants les goulots d'etranglements et en les gérants \n" +
                        "B- Il faut menacer les travailleurs \n" +
                        "C- La réponse C \n",
                "C");

        Question question3Qualite = new Question(3,
                "Qu'est ce que Ishikawa ? \n" +
                        "A- Un graphique de causes et effets \n" +
                        "B- Un outils de gestions de normes \n" +
                        "C- Une méchante technique d'immobilisation (franchement balèze) \n",
                "A");

        profQualite.ajouterQuestion(question1Qualite);
        profQualite.ajouterQuestion(question2Qualite);
        profQualite.ajouterQuestion(question3Qualite);
        return profQualite;
    }
    private PNJ_Prof getPnjProfAnglais(){
        PNJ_Prof profAnglais = new PNJ_Prof("PROF_ANG",
                "1 -Professeur d'anglais",
                new String[]{"Hello !", "Ready for class ?"},
                ARTICLE_BANGLADESH,
                60,
                "Anglais");

        Question question1 = new Question(1,
                "How are you ? \n" +
                        "A- Yellow. \n" +
                        "B- Fine, thanks a lot \n" +
                        "C- Muy bien i tu ? \n",
                "B");

        Question question2 = new Question(2,
                "Is it a good situation to be in vocational training? \n" +
                        "A- привет \n" +
                        "B- I like trains a lot yes \n" +
                        "C- Yes, i like to practice and improve my professional skills ! \n",
                "C");

        Question question3 = new Question(3,
                "What do you think about AI ? \n" +
                        "A- It can be a usefully thing, if it's only used as a tool, not a way to achieve things  \n" +
                        "B- It's a good spice but you mean garlic, isn't it ? \n" +
                        "C- I think you are a good teacher, but that's not very professional to ask that no ? \n",
                "A");

        profAnglais.ajouterQuestion(question1);
        profAnglais.ajouterQuestion(question2);
        profAnglais.ajouterQuestion(question3);
        return profAnglais;
    }
    private PNJ_Prof getPnjProfAlgo(){

        PNJ_Prof profAlgo = new PNJ_Prof("PROF_ALGO",
                "1 - Professeur d'algorithmique",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                ON2,
                60,
                "Algorithmique");

        Question question1 = new Question(1,
                "Qu'est ce que la complexité ? \n" +
                        "A- C'est dur. \n" +
                        "B- L'étude de la quantité de ressources (par exemple de temps ou d'espace) nécessaire à l'exécution d'un algorithme.  \n" +
                        "C- Je ne sais pas \n",
                "B");

        Question question2 = new Question(2,
                "La complexité d'un if () ? \n" +
                        "A- 1 \n" +
                        "B- n \n" +
                        "C- Oui \n",
                "C");

        Question question3 = new Question(3,
                "Un algorithme c'est ? \n" +
                        "A- Une description d'une suite d'étapes permettant d'obtenir un résultat à partir d'éléments fournis en entrée.  \n" +
                        "B- Du truc magique qui fait pleins de choses \n" +
                        "C- Bonne question \n",
                "A");

        profAlgo.ajouterQuestion(question1);
        profAlgo.ajouterQuestion(question2);
        profAlgo.ajouterQuestion(question3);
        return profAlgo;
    }
    private PNJ_Prof getPnjProfGestion(){

        PNJ_Prof profGestion = new PNJ_Prof("PROF_GEST",
                "1 -Professeur de gestion",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                SCRUM_BOOK,
                60,
                "Gestion de projet SCRUM");

        Question question1 = new Question(1,
                "C'est quoi la méthode AGILE ? \n" +
                        "A- C'est une approche qui permet d'apporter souplesse et performance à la gestion de projet et centrée sur l'humain et la communication \n" +
                        "B- C'est une méthode où on organise un projet de manière stricte et organisée \n" +
                        "C- Le fait d'être souple \n",
                "A");

        Question question2 = new Question(2,
                "Les 3 piliers SCRUM sont ? \n" +
                        "A- Procrastination \n" +
                        "B- Post-It \n" +
                        "C- Transparence, Inspection et Adaptation \n",
                "C");

        Question question3 = new Question(3,
                "Qu'est ce qu'un SCRUM Master ? \n" +
                        "A- Assurer l'implication de chaque membre et de les aider à franchir les différents obstacles qu'ils pourraient rencontrer \n" +
                        "B- Les gouverner tous. Les trouver. Un Anneau pour les amener tous et dans les ténèbres les lier. \n" +
                        "C- Spécifique Mesurable Atteignable Réaliste Transmissible \n",
                "A");

        profGestion.ajouterQuestion(question1);
        profGestion.ajouterQuestion(question2);
        profGestion.ajouterQuestion(question3);
        return profGestion;
    }
    private PNJ_Prof getPnjProfWooclip(){
        PNJ_Prof profWooclip = new PNJ_Prof("PROF_GEST",
                "2 -Ordinateur",
                new String[]{"BIENVENUE DANS LE QUIZZ WOOCLOP, PREPAREZ VOUS POUR LE TEST"},
                CLE_USB,
                60,
                "Gestion de projet");

        Question question1 = new Question(1,
                "C'est quoi un KANBAN ? \n" +
                        "A- Un système de planification pour la fabrication à flux tendus \n" +
                        "B- Une méthode d'intégration \n" +
                        "C- Une onomatopée \n",
                "A");

        Question question2 = new Question(2,
                "Qu'est ce qu'un jour homme ? \n" +
                        "A- L'inverse d'un jour femme \n" +
                        "B- Un jour réservé aux hommes \n" +
                        "C- Une unité de mesure correspondant au travail d'une personne pendant une journée \n",
                "C");

        Question question3 = new Question(3,
                "Que veut dire SMART ? \n" +
                        "A- Spécifique Mesurable Atteignable Réaliste Temporairement défini  \n" +
                        "B- Saucisson Moutarde Allouette Raclette Toboggan \n" +
                        "C- Spécifique Mesurable Atteignable Réaliste Transmissible \n",
                "A");

        profWooclip.ajouterQuestion(question1);
        profWooclip.ajouterQuestion(question2);
        profWooclip.ajouterQuestion(question3);
        return profWooclip;
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
        gui.afficher("=== AIDE ===");
        gui.afficher();
        gui.afficher("Commandes de déplacement :");
        gui.afficher("- ALLER [direction] : N (Nord), S (Sud), E (Est), O (Ouest), H (Haut), B (Bas)");
        gui.afficher("- RETOUR : Retourner à la zone précédente");
        gui.afficher("- TELEPORTER : Se téléporter à une zone");
        gui.afficher();
        gui.afficher("Commandes d'interaction :");
        gui.afficher("- INSPECTER : Examiner la zone actuelle");
        gui.afficher("- PRENDRE [objet] : Prendre un objet (ou PRENDRE si un seul objet)");
        gui.afficher("- OUVRIR [conteneur] : Ouvrir un conteneur (ou OUVRIR pour le coffre)");
        gui.afficher("- PARLER [personnage] : Parler à un personnage");
        gui.afficher("- UTILISER [objet] : Utiliser un objet de l'inventaire");
        gui.afficher();
        gui.afficher("Commandes de gestion :");
        gui.afficher("- INVENTAIRE : Afficher votre inventaire");
        gui.afficher("- CONNEXION [PSEUDO] : Se connecter au jeu");
        gui.afficher("- SAUVEGARDER : Sauvegarder la partie");
        gui.afficher("- CHARGER : Charger une partie sauvegardée");
        gui.afficher("- QUITTER : Quitter le jeu");
        gui.afficher();
        gui.afficher("=== FIN AIDE ===");
        gui.afficher();
    }
    
    private void terminer() {
        if(actualPlayer.isLogged()){
            sauvegarderJeu();
        }
        threadCompteur.interrupt();
    	gui.afficher( "Au revoir...");
    	gui.enable( false);
        System.exit(0);
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
