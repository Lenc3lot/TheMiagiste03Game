package jeu;

import jeu.PNJ.PNJ_Admin;
import jeu.PNJ.PNJ_Guide;
import jeu.PNJ.PNJ_Prof;
import jeu.PNJ.PNJ_ZamZam;
import netscape.javascript.JSObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Classe principale gérant l'ensemble du jeu d'aventure "The Miagiste 03".
 *
 * Cette classe contrôle la navigation entre les zones, les interactions avec les PNJ,
 * la gestion de l'inventaire, la logique du temps (via le compteur) et les mécanismes de sauvegarde/chargement.
 *
 * Elle orchestre aussi la logique du déroulement du scénario et des quiz pour obtenir les objets clés.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */

public class Jeu implements Serializable {

    /** Interface graphique du jeu. */
    private GUI gui;

    /** Zone actuelle où se trouve le joueur. */
	private Zone zoneCourante;

    /** État actuel de la partie pour sauvegarde/chargement. */
    private Sauvegarde actualGameState;

    /** Joueur actuel. */
    private Joueur actualPlayer;

    /** Historique des zones traversées (pour la commande RETOUR). */
    private Stack<Zone> historiqueZones;

    /** Compteur de temps du jeu. */
    private Compteur compteur;

    /** Thread gérant le compteur. */
    private Thread threadCompteur;

    /** Indique si le temps est écoulé. */
    private boolean isTimeOut = false;

    // CREATION DES OBJETS
    /**
     * Objet clé : Certification MIAGE, indispensable pour valider la fin du jeu.
     */
    public static final Objet CERTIFICATION = new Objet("CERTIF", "Certification MIAGE", true, true,
            "Votre certification MIAGE qui sert aussi de TODO list");

    /**
     * Objet clé : Maison de qualité, récupérée pendant le cours de qualité.
     */
    public static final Objet MAISON = new Objet("MAISON", "Maison de qualité", true, true,
            "Une représentation de la maison de la qualité en management");

    /**
     * Objet clé : Guide d'intégration, récupéré pendant le cours de transformation numérique.
     */
    public static final Objet GUIDE_INTEGRATION = new Objet("GUIDE", "Guide d'intégration", true, true,
            "Guide pour l'intégration en transformation numérique");

    /**
     * Objet clé : Article sur le Bangladesh, récupéré pendant le cours d'anglais.
     */
    public static final Objet ARTICLE_BANGLADESH = new Objet("ARTICLE", "Article sur le Bangladesh", true, true,
            "Article géopolitique sur le Bangladesh");

    /**
     * Objet clé : Complexité algorithmique O(n²), récupéré pendant le cours d'algorithmique.
     */
    public static final Objet ON2 = new Objet("ON2", "O(n²)", true, true,
            "Représentation de la complexité algorithmique");

    /**
     * Objet clé : Livre Scrum, récupéré pendant le cours de gestion de projet SCRUM.
     */
    public static final Objet SCRUM_BOOK = new Objet("SCRUM", "Scrum Book", true, true,
            "Livre sur la méthodologie Scrum");

    /**
     * Objet clé : Clé USB contenant des supports Wooclap.
     */
    public static final Objet CLE_USB = new Objet("USB", "Clé USB Wooclap", true, true,
            "Clé USB contenant les présentations Wooclap");

    /**
     * Objet bonus : Chat GPT, un assistant pour aider pendant les quiz.
     */
    public static final Objet CHAT_GPT = new Objet("GPT", "Chat GPT", true, true,
            "Assistant d'intelligence artificielle");

    /**
     * Objet bonus : Kebab du ZAM ZAM, permet de modifier le temps dans la partie.
     */
    public static final Objet KEBAB = new Objet("KEBAB", "Kebab du ZAM ZAM", false, true,
            "Un délicieux kebab du ZAM ZAM");

    /**
     * Objet bonus : Café pour gagner en énergie.
     */
    public static final Objet CAFE = new Objet("CAFE", "Café", false, true,
            "Un bon café pour se réveiller");

    /** Dictionnaire de correspondance des directions abrégées vers les directions complètes. */
    private static final Map<String, String> directionsMap = Map.of(
            "N", "NORD",
            "S", "SUD",
            "E", "EST",
            "O", "OUEST",
            "H", "HAUT",
            "B", "BAS"
    );

    /**
     * Constructeur du jeu.
     * Initialise la carte et les composants principaux.
     */
    public Jeu() {
        creerCarte();
        gui = null;
        historiqueZones = new Stack<>();
        actualPlayer = new Joueur("newJoueur");
        actualGameState = new Sauvegarde();
    }

    /**
     * Associe l'interface graphique au jeu.
     *
     * @param g Instance de l'interface graphique.
     */
    public void setGUI( GUI g) {
        gui = g; afficherMessageDeBienvenue();
    }

    /**
     * Sauvegarde l'état actuel de la partie.
     */
    public void sauvegarderJeu(){
        actualGameState.setMember("playerPseudo",actualPlayer.getPseudo());
        actualGameState.setMember("zoneCourante",zoneCourante);
        actualGameState.setMember("inventaireJoueur",actualPlayer.getInventaireJoueur());
        actualGameState.setMember("historiqueZones",historiqueZones);
        actualGameState.setMember("acutalTimeLeft",compteur.getTimeLeft());
        actualGameState.setMember("player",actualPlayer);
        actualGameState.writeSave();
    }

    /**
     * Traite la commande saisie par le joueur.
     *
     * @param commandeLue Commande tapée par le joueur.
     */
    public void traiterCommande(String commandeLue) {
        if(!isTimeOut){
            gui.afficher( "> "+ commandeLue + "\n");
            String[] parties = commandeLue.split(" ", 2);
            String commande = parties[0].toUpperCase();
            String parametre = parties.length > 1 ? parties[1].toUpperCase() : "";

            if(actualPlayer.getTalkingTo() != null){
                checkReponse(commande);
            } else if (actualPlayer.getIsFinaleExamStarted()){

            } else {
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
                            HashMap<String, Object> loadSave = actualGameState.loadSave();
                            compteur.setTimeBack(Integer.parseInt(loadSave.get("acutalTimeLeft").toString()));
                            setZoneCourante((Zone) loadSave.get("zoneCourante"));
                            afficherLocalisation();
                            gui.afficheImage(zoneCourante.nomImage());
                            historiqueZones = (Stack<Zone>) loadSave.get("historiqueZones");
                            //actualPlayer.setInventaireJoueur((Inventaire) loadSave.get("inventaireJoueur"));
                            actualPlayer = (Joueur) loadSave.get("player");
                            gui.afficher("Partie chargée !");
                        }
                        break;
                    default:
                        gui.afficher("Commande inconnue");
                        break;
                }
            }
        } else {
            gui.afficher("Le temps est écoulé ! Vous pouvez recommencer en relancant une partie !");
            gui.afficher();
        }
    }

    /**
     * Indique que le temps est écoulé et termine la partie.
     */
    public void timeIsOut() {
        gui.afficher("Le temps est écoulé ! Vous n'avez pas réussi à obtenir votre diplôme à temps...");
        gui.afficher();
        isTimeOut = true;
    }

    /**
     * Permet d'aller dans une direction donnée.
     *
     * @param direction Direction souhaitée.
     */
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

    /**
     * Convertit une direction abrégée en direction complète ou retourne null si invalide.
     *
     * @param direction Direction saisie.
     * @return Direction complète ou null.
     */
    private String directionValideOuNull(String direction) {
        if (direction == null || direction.trim().isEmpty()) return null;
        String directionClean = direction.trim().toUpperCase();
        return directionsMap.getOrDefault(directionClean, directionClean);
    }

    /**
     * Permet de parler à un PNJ présent dans la zone.
     *
     * @param personnage Nom du personnage.
     */
    private void parlerA(String personnage) {
        String message = actualPlayer.parler(personnage, zoneCourante, compteur);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }

    /**
     * Vérifie la réponse du joueur lors d'un quiz.
     *
     * @param reponse Réponse donnée par le joueur.
     */
    private void checkReponse(String reponse){
        if(actualPlayer.getTalkingTo() instanceof PNJ_Prof talkingTo){
            if(talkingTo.isAnswerTrue(reponse)){
                gui.afficher("Bravo pour cet examen ! \n" +
                        "[Tu as obtenu : "+talkingTo.giveItem().getLabel() +"]");
                gui.afficher();
                actualPlayer.getInventaireJoueur().ajouterObjet(talkingTo.giveItem());
                actualPlayer.setTalkingTo(null);
                actualPlayer.getEmploiDuTemps().passerAuQuizSuivant();
                zoneCourante.changerImage(zoneCourante.nomImage().replace("Avec", "Sans"));
                gui.afficheImage(zoneCourante.nomImage());
            }else{
                compteur.setTimeBack(compteur.getTimeLeft() + 30);
                gui.afficher("MAUVAISE REPONSE ! Je repose ma question :  \n"+
                        talkingTo.getQuestionPosee().getTextQuestion()
                );
                gui.afficher();
            }
        }else if(actualPlayer.getTalkingTo() instanceof PNJ_Admin talkingTo){
            if(talkingTo.getQuestionFinale().getReponseQuestion().equals(reponse)){
                gui.afficher("Félicitation ! Vous avez réussi vous avez obtenu votre dîplôme ! \n" +
                        "Bravo ! \n" +
                        "\n" +
                        "[FELICITATION ! VOUS OBTENEZ LE DIPLOME MIAGE]");
                Objet diplome = new Objet("DIPLOME","Diplome Miagiste",false,true,"Un magnifique diplomé décerné par la MIAGE à "+actualPlayer.getPseudo());
                actualPlayer.getInventaireJoueur().ajouterObjet(diplome);
                actualPlayer.setTalkingTo(null);
            }else{
                gui.afficher("Vous êtes sûr d'avoir été en MIAGE ?... \n" +
                        "Je vous laisse réfléchir un peu de temps avant de me répondre à nouveau... \n" +
                        "[PENALITE DE 30S]");
                compteur.setTimeBack(getCompteur().getTimeLeft()+30);
            }
        }
    }

    /**
     * Permet au joueur de prendre un objet dans la zone.
     *
     * @param nomObjet Nom de l'objet.
     */
    private void prendreObjet(String nomObjet) {
        String message = actualPlayer.prendreObjet(nomObjet, zoneCourante);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }

    /**
     * Affiche l'inventaire du joueur.
     */
    private void afficherInventaire() {
        String message = actualPlayer.afficherInventaire();
        gui.afficher(message);
    }

    /**
     * Permet d'inspecter un élément ou la zone actuelle.
     *
     * @param element Élément à inspecter.
     */
    private void inspecter(String element) {
        String message = actualPlayer.inspecter(element, zoneCourante);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }

    /**
     * Permet d'ouvrir un conteneur.
     *
     * @param conteneur Nom du conteneur.
     */
    private void ouvrirConteneur(String conteneur) {
        String message = actualPlayer.ouvrir(conteneur, zoneCourante);
        gui.afficher(message);
        gui.afficheImage(zoneCourante.nomImage());
    }

    /**
     * Permet d'utiliser un objet de l'inventaire.
     *
     * @param objet Nom de l'objet.
     */
    private void utiliserObjet(String objet) {
        String message = actualPlayer.utiliser(objet, compteur);
        gui.afficher(message);
    }

    /**
     * Crée toute la carte du jeu, les zones et leurs connexions.
     */
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

    /**
     * Crée et configure le professeur de Transformation Numérique.
     *
     * @return PNJ_Prof représentant le professeur de Transformation.
     */
    private PNJ_Prof getPnjProfTransfo() {
        PNJ_Prof profTransfo = new PNJ_Prof("PROF_TRANSFO",
                "1 -Professeur de Transformation",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                GUIDE_INTEGRATION,
                "Transformation numérique",
                1);

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

    /**
     * Crée et configure le professeur de Gestion de la Qualité.
     *
     * @return PNJ_Prof représentant le professeur de Qualité.
     */
    private PNJ_Prof getPnjProfQualite(){
        PNJ_Prof profQualite = new PNJ_Prof("PROF_QUAL",
                "1 -Professeur de Qualité",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                MAISON,
                "Gestion de la qualité",
                2);

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
                "A");

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

    /**
     * Crée et configure le professeur d'Anglais.
     *
     * @return PNJ_Prof représentant le professeur d'Anglais.
     */
    private PNJ_Prof getPnjProfAnglais(){
        PNJ_Prof profAnglais = new PNJ_Prof("PROF_ANG",
                "1 -Professeur d'Anglais",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                ARTICLE_BANGLADESH,
                "Anglais des affaires",
                3);

        Question question1 = new Question(1,
                "How are you ? \n" +
                        "A- Yellow. \n" +
                        "B- Fine, thanks a lot \n" +
                        "C- Muy bien i tu ? \n",
                "B");

        Question question2 = new Question(2,
                "Is it a good situation to be in vocational training? \n" +
                        "A- \n" +
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

    /**
     * Crée et configure le professeur d'Algorithmique.
     *
     * @return PNJ_Prof représentant le professeur d'Algorithmique.
     */
    private PNJ_Prof getPnjProfAlgo(){

        PNJ_Prof profAlgo = new PNJ_Prof("PROF_ALGO",
                "1 - Professeur d'algorithmique",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                ON2,
                "Algorithmique",
                4);

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
                        "B- Un truc magique qui fait pleins de choses \n" +
                        "C- Bonne question \n",
                "A");

        profAlgo.ajouterQuestion(question1);
        profAlgo.ajouterQuestion(question2);
        profAlgo.ajouterQuestion(question3);
        return profAlgo;
    }

    /**
     * Crée et configure le professeur de Gestion de Projet (SCRUM).
     *
     * @return PNJ_Prof représentant le professeur de Gestion.
     */
    private PNJ_Prof getPnjProfGestion(){

        PNJ_Prof profGestion = new PNJ_Prof("PROF_GEST",
                "1 -Professeur de Gestion",
                new String[]{"Bonjour !", "Prêt pour le cours ?"},
                SCRUM_BOOK,
                "Gestion de projet SCRUM",
                5);

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
                "Qu'est ce que fait SCRUM Master ? \n" +
                        "A- Assurer l'implication de chaque membre et de les aider à franchir les différents obstacles qu'ils pourraient rencontrer \n" +
                        "B- Les gouverner tous. Les trouver. Et pour les amener tous et dans les ténèbres les lier. \n" +
                        "C- Spécifique Mesurable Atteignable Réaliste Transmissible \n",
                "A");

        profGestion.ajouterQuestion(question1);
        profGestion.ajouterQuestion(question2);
        profGestion.ajouterQuestion(question3);
        return profGestion;
    }

    /**
     * Crée et configure le professeur responsable du quiz Wooclap.
     *
     * @return PNJ_Prof représentant le professeur responsable du quiz Wooclap.
     */
    private PNJ_Prof getPnjProfWooclip(){
        PNJ_Prof profWooclip = new PNJ_Prof("WOOCLIP",
                "2 -Ordinateur",
                new String[]{"BIENVENUE DANS LE QUIZZ WOOCLOP, PREPAREZ VOUS POUR LE TEST"},
                CLE_USB,
                "Utilisation de Wooclap",
                6);

        Question question1 = new Question(1,
                "C'est quoi un KANBAN ? \n" +
                        "A- Un système de planification pour la fabrication à flux tendus \n" +
                        "B- Une méthode d'intégration \n" +
                        "C- Une onomatopée de BD\n",
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

    /**
     * Affiche la localisation actuelle du joueur.
     */
    private void afficherLocalisation() {
        gui.afficher( zoneCourante.descriptionLongue());
        gui.afficher();
    }

    /**
     * Affiche un message de bienvenue au démarrage du jeu.
     */
    private void afficherMessageDeBienvenue() {
        gui.afficher(UIHelper.line());
        gui.afficher(UIHelper.center("BIENVENUE À MIAGE !"));
        gui.afficher(UIHelper.line());
        gui.afficher(UIHelper.left("Tapez '?' pour obtenir de l'aide."));
        gui.afficher(UIHelper.line());
        gui.afficher("");
        afficherLocalisation();
        gui.afficheImage(zoneCourante.nomImage());
    }

    /**
     * Permet de revenir à la zone précédente.
     */
    private void retourner() {
        if (!historiqueZones.isEmpty()) {
            zoneCourante = historiqueZones.pop();
            afficherLocalisation();
            gui.afficheImage(zoneCourante.nomImage());
        } else {
            gui.afficher("Aucune zone précédente");
        }
    }

    /**
     * Permet de téléporter le joueur.
     */
    private void teleporter() {
        // TODO: Implémenter la logique de téléportation
        // Afficher la liste des zones disponibles
        // Permettre au joueur de choisir une zone
    }

    /**
     * Affiche l'aide pour les commandes du jeu.
     */
    private void afficherAide() {
        gui.afficher("\n+---------------- GUIDE DU JEU MIAGE ----------------+\n");
        gui.afficher("Déplacements :\n" +
            "  > ALLER N/S/E/O/H/B   : Se déplacer\n" +
            "  > RETOUR              : Zone précédente\n" +
            "  > TELEPORTER          : Changer de zone\n");
        gui.afficher("Interactions :\n" +
            "  > PARLER [nom]        : Dialoguer\n" +
            "  > INSPECTER           : Observer la zone\n" +
            "  > OUVRIR              : Ouvrir un conteneur\n");
        gui.afficher("Objets :\n" +
            "  > INVENTAIRE          : Voir les objets\n" +
            "  > PRENDRE [objet]     : Ramasser\n" +
            "  > UTILISER [objet]    : Utiliser\n");
        gui.afficher("Gestion :\n" +
            "  > CONNEXION [pseudo]  : Se connecter\n" +
            "  > SAUVEGARDER         : Sauver la partie\n" +
            "  > CHARGER             : Charger une partie\n" +
            "  > QUITTER             : Quitter le jeu\n");
        gui.afficher("\nTapez '?' à tout moment pour revoir ce menu");
        gui.afficher("+---------------------------------------------------+\n");
    }

    /**
     * Termine proprement le jeu et ferme l'application.
     */
    private void terminer() {
        if(actualPlayer.isLogged()){
            sauvegarderJeu();
        }
        threadCompteur.interrupt();
    	gui.afficher( "Au revoir...");
    	gui.enable( false);
        System.exit(0);
    }

    /**
     * Retourne l'état actuel de la sauvegarde.
     *
     * @return JSObject représentant la sauvegarde.
     */
    public JSObject getActualGameState(){
        return this.actualGameState;
    }

    /**
     * Définit la zone courante.
     *
     * @param zoneCourante Nouvelle zone actuelle.
     */
    public void setZoneCourante(Zone zoneCourante){
        this.zoneCourante = zoneCourante;
    }

    /**
     * Définit le compteur du jeu ainsi que son thread associé.
     *
     * @param actualCompteur Compteur à utiliser.
     * @param threadCompteur Thread du compteur.
     */
    public void setCompteur(Compteur actualCompteur, Thread threadCompteur){
        this.compteur = actualCompteur;
        this.threadCompteur = threadCompteur;
    }

    /**
     * Retourne l'instance du compteur.
     *
     * @return Compteur du jeu.
     */
    public Compteur getCompteur() {
        return compteur;
    }
}
