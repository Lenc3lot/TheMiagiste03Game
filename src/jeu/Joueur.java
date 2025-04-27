package jeu;

import jeu.PNJ.*;

import java.io.Serializable;

import java.io.Serializable;
import java.util.List;

/**
 * Classe représentant le joueur dans le jeu d'aventure.
 *
 * Le joueur possède un pseudo, un inventaire, un état de connexion, ainsi que diverses informations
 * sur sa progression (rencontre avec SIN, Sandrine, obtention du statut d'étudiant, etc.).
 *
 * Il peut interagir avec les PNJ, récupérer des objets, utiliser des objets et suivre son emploi du temps.
 *
 * Cette classe est sérialisable pour permettre la sauvegarde de la progression du joueur.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */

public class    Joueur  implements Serializable{

    /** Pseudo du joueur. */
    private String pseudo;

    /** Inventaire du joueur. */
    private Inventaire inventaireJoueur;

    /** Indique si le joueur est connecté. */
    private boolean isLogged;
    private PNJ talkingTo;
    private boolean isFinalExamStarted = false;
    private boolean aRencontreSin;

    /** Indique si le joueur a rencontré Sandrine. */
    private boolean aRencontreSandrine = false;

    /** Indique si le joueur a officiellement le statut d'étudiant MIAGE. */
    private boolean estEtudiant;
    private transient Jeu jeu;

    /** Nombre de téléportations restantes pour le cours actuel. */
    private int teleportationsRestantes = 2;

    /**
     * Constructeur du joueur.
     *
     * @param unPseudo Pseudo choisi par le joueur.
     */
    public Joueur(String unPseudo) {
        this.pseudo = unPseudo;
        this.inventaireJoueur = new Inventaire();
        this.isLogged = false;
        this.aRencontreSin = false;;
        this.estEtudiant = false;
    }

    /**
     * Permet au joueur de parler à un PNJ présent dans la zone.
     *
     * @param personnage Nom du personnage avec lequel interagir.
     * @param zone Zone actuelle où se trouve le joueur.
     * @return Réponse ou interaction générée par le PNJ.
     */
    public String parler(String personnage, Zone zone, Compteur compteur) {
        // ################################################################################################# VERIFIE personnage null
        List<PNJ> pnjs = zone.getPNJs();
        if (personnage == null || personnage.trim().isEmpty()) {
            if (pnjs.isEmpty()) {
                return UIHelper.box("Il n'y a personne ici à qui parler.");
            }

            StringBuilder message = new StringBuilder("Personnes présentes :\n");
            int index = 1;
            for (PNJ pnj : pnjs) {
                message.append(index).append(". ").append(pnj.getNomPNJ()).append("\n");
                index++;
            }
            message.append("\nPour parler à quelqu'un, utilisez PARLER suivi de son numéro ou de son nom.");
            return UIHelper.box(message.toString());
        }
        personnage = personnage.trim().toLowerCase();

        // ################################################################################################# Vérifier si l'utilisateur a entré un numéro
        try {
            int numero = Integer.parseInt(personnage);
            if (numero > 0 && numero <= pnjs.size()) {
                PNJ pnj = pnjs.get(numero - 1);
                String[] pngId = pnj.getNomPNJ().split(" ", 2);
                personnage = pngId[0].toLowerCase();
            }
        } catch (NumberFormatException e) {
            // Ce n'est pas un numéro, on continue avec le nom
        }

        //################################################################################################# POUR CHAQUE PNJ DE LA LISTE DE PNJ
        for (PNJ pnj : pnjs) {
            String[] pngId = pnj.getNomPNJ().split(" ", 2);

            // ################################################################################################# SI ID DU PNJ = SAISIE USER
            if (pngId[0].toLowerCase().equals(personnage)) {
                // #################################################################################################Cas parler à PNJ SANS ETRE ETUDIANT MIAGE
                if (!estEtudiant && !personnage.equals("sin") && !personnage.equals("sandrine")) {
                    return UIHelper.box("Vous n'êtes pas encore un étudiant de la MIAGE. Vous devez d'abord rencontrer SIN puis SANDRINE.");
                }

                // ################################################################################################# INSTANCE DE GUIDE
                if (pnj instanceof PNJ_Guide) {
                    PNJ_Guide guide = (PNJ_Guide) pnj;
                    if (personnage.equals("sin")) {
                        aRencontreSin = true;
                    }
                    return UIHelper.box(guide.donnerIndice());
                } else if (pnj instanceof PNJ_Admin admin) { // ################################################################################################# INSTANCE ADMIN
                    if (personnage.equals("sin")) { // ################################################################################################# SI ON PARLE A SIN
                        aRencontreSin = true;
                        return UIHelper.box("SIN : Bienvenue à la MIAGE ! Vous devez maintenant aller voir SANDRINE pour obtenir votre emploi du temps.");
                    } else if (personnage.equals("sandrine")) { // ################################################################################################# SI ON PARLE A SANDRINE

                        if (!aRencontreSin) { // ################################################################################################# SI ON A PAS RENCONTRE SIN
                            return UIHelper.box("SANDRINE : Désolée, mais vous devez d'abord rencontrer SIN avant de venir me voir.");
                        }

                        if (!aRencontreSandrine) { // ################################################################################################CAS OU A PAS ENCORE RENCONTRE SANDRINE
                            estEtudiant = true;
                            // Création de l'emploi du temps
                            List<Integer> listeQuizs = admin.getListeQuizs();
                            EmploiDuTemps emploiDuTemps = new EmploiDuTemps(listeQuizs);
                            emploiDuTemps.validerEmploiDuTemps();
                            inventaireJoueur.ajouterObjet(emploiDuTemps);
                            aRencontreSandrine = true;
                            zone.changerImage(zone.nomImage().replace("Avec", "Sans"));
                        } else if (aRencontreSandrine && admin.isAllKeyItemsCollected(inventaireJoueur, compteur)) { // ################################################# CAS DEMARAGE EXAM FINAL
                            setTalkingTo(admin);
                            return admin.startExamFinal();
                        }
                        return UIHelper.box(admin.donnerEmploiDuTemps());
                    }
                } else if (pnj instanceof PNJ_ZamZam) { // CAS PNJ ZAM ZAM
                    // ################################################################################################ VERIFIE L HEURE
                    int minutes = 0;
                    int heures = 0;
                    if (compteur != null) {
                        int time = compteur.getTimeLeft();
                        heures = ((time % 86400) % 3600) / 60;
                        minutes = ((time % 86400) % 3600) % 60;
                    }
                    if (heures < 11 || heures >= 12) {
                        return UIHelper.box("Le chef du ZAM ZAM n'est disponible que de 11h à 12h ! (Heure actuelle : " + heures + "h" + String.format("%02d", minutes) + ")");
                    }

                    PNJ_ZamZam zamZam = (PNJ_ZamZam) pnj;
                    Objet sandwich = zamZam.donnerSandwich();
                    if (sandwich != null) {
                        inventaireJoueur.ajouterObjet(sandwich);
                        zone.changerImage(zone.nomImage().replace("Avec", "Sans"));
                        return UIHelper.box("Voici un magnifique sandwich pour vous donner des forces !\nVous pourrez l'utiliser quand vous en aurez besoin.\nVous avez reçu : " + sandwich.getLabel());
                    } else {
                        return UIHelper.box("Vous avez déjà reçu votre sandwich !");
                    }

                } else if (pnj instanceof PNJ_Prof prof) {// CAS PNJ PROF
                    EmploiDuTemps emploiDuTemps = getEmploiDuTemps();
                    if (emploiDuTemps == null) {
                        return UIHelper.box("Vous devez d'abord obtenir votre emploi du temps chez SANDRINE.");
                    }
                    if (!emploiDuTemps.estValide()) {
                        return UIHelper.box("Votre emploi du temps n'est pas encore valide.");
                    }
                    if (!emploiDuTemps.peutFaireQuiz(prof.getIdQuiz())) {
                        return UIHelper.box("Ce n'est pas le moment de faire ce quiz. Consultez votre emploi du temps.");
                    }
                    if (!prof.hasGivenQuiz()) {
                        setTalkingTo(prof);
                        return UIHelper.box(prof.donnerQuiz());
                    }
                } else {
                    StringBuilder message = new StringBuilder();
                    for (String dialogue : pnj.getTexteInterraction()) {
                        message.append(dialogue).append("\n");
                    }
                    return UIHelper.box(message.toString());
                }
            }
        }
        return UIHelper.box("La personne que vous cherchez n'est pas ici.");
    }

    /**
     * Récupère l'emploi du temps du joueur.
     *
     * @return EmploiDuTemps si présent, sinon null.
     */
    public EmploiDuTemps getEmploiDuTemps() {
        List<Objet> objets = inventaireJoueur.getObjets();
        for (Objet obj : objets) {
            if (obj instanceof EmploiDuTemps) {
                return (EmploiDuTemps) obj;
            }
        }
        return null;
    }

    /**
     * Permet au joueur d'inspecter une zone ou ses éléments.
     *
     * @param element Élément spécifique à inspecter.
     * @param zone Zone actuelle.
     * @return Résultat de l'inspection.
     */
    public String inspecter(String element, Zone zone) {
        // Si pas de paramètre, on inspecte la zone par défaut
        if (element == null || element.trim().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Vous examinez la zone :\n");

            // Afficher les objets
            List<Objet> objets = zone.getObjets();
            if (!objets.isEmpty()) {
                sb.append("Objets visibles :\n");
                for (Objet obj : objets) {
                    sb.append("- ").append(obj.getLabel()).append("\n");
                }
            }

            // Afficher les coffres
            if (zone.toString().equals("Bureau BDE")) {
                sb.append("Coffres :\n");
                sb.append("- Un coffre ");
                if (zone.nomImage().equals("bureauBdeCoffreOuvert.png")) {
                    sb.append("ouvert");
                } else {
                    sb.append("fermé");
                }
            }

            return sb.toString();
        }

        return "Il n'y a rien à examiner ici.";
    }

    /**
     * Permet d'ouvrir un conteneur dans la zone.
     *
     * @param conteneur Nom du conteneur à ouvrir.
     * @param zone Zone actuelle.
     * @return Résultat de l'ouverture.
     */
    public String ouvrir(String conteneur, Zone zone) {
        // Si pas de paramètre et qu'on est dans le bureau BDE, on ouvre le coffre par défaut
        if (conteneur == null || conteneur.trim().isEmpty()) {
            if (zone.toString().equals("Bureau BDE")) {
                if (zone.nomImage().equals("bureauBdeCoffreOuvert.png")) {
                    return "Le coffre est déjà ouvert !";
                }

                zone.changerImage("bureauBdeCoffreOuvert.png");

                Objet mascotte = new Objet("MASCOTTE", "Mascotte du BDE", true, true,
                        "La mascotte officielle du BDE");;
                inventaireJoueur.ajouterObjet(mascotte);

                return "BRAVO ! Vous avez trouvé la mascotte du BDE !";
            }
            return "Que voulez-vous ouvrir ?";
        }

        if (zone.toString().equals("Bureau BDE") && conteneur.equalsIgnoreCase("COFFRE")) {
            if (zone.nomImage().equals("bureauBdeCoffreOuvert.png")) {
                return "Le coffre est déjà ouvert !";
            }

            zone.changerImage("bureauBdeCoffreOuvert.png");

            Objet mascotte = new Objet("MASCOTTE", "Mascotte du BDE", true, true,
                    "La mascotte officielle du BDE");;
            inventaireJoueur.ajouterObjet(mascotte);

            return "BRAVO ! Vous avez trouvé la mascotte du BDE !";
        }

        return "Il n'y a pas de '" + conteneur + "' à ouvrir ici.";
    }

    /**
     * Permet d'utiliser un objet de l'inventaire.
     *
     * @param objet Nom de l'objet à utiliser.
     * @return Résultat de l'utilisation.
     */
    public String utiliser(String objet, Compteur compteur) {

        if (objet == null || objet.trim().isEmpty()) {
            return "Quel objet voulez-vous utiliser ? Consultez votre inventaire pour voir les objets disponibles.";
        }

        objet = objet.trim().toUpperCase();
        List<Objet> objets = inventaireJoueur.getObjets();
        Objet objetTrouve = null;

        for (Objet obj : objets) {
            if (obj.getLabel().toUpperCase().equals(objet) ||
                (obj.getIdObjet() != null && obj.getIdObjet().toUpperCase().equals(objet))) {
                objetTrouve = obj;
                break;
            }
        }

        if (objetTrouve == null) {
            return "Vous n'avez pas cet objet dans votre inventaire. Consultez votre inventaire pour voir les objets disponibles.";
        }

        if (!objetTrouve.isUsable()) {
            return "Vous ne pouvez pas utiliser cet objet pour le moment.";
        }

        if (objetTrouve instanceof EmploiDuTemps) {
            return objetTrouve.toString();
        }

        // Gestion spéciale pour le sandwich du ZAM ZAM
        if (objetTrouve.getLabel().toUpperCase().contains("SANDWICH")) {
            java.util.Random rand = new java.util.Random();
            boolean bon = rand.nextBoolean();
            inventaireJoueur.retirerObjet(objetTrouve); // On retire le sandwich après usage
            if (compteur != null) {
                int time = compteur.getTimeLeft();
                if (bon) {
                    compteur.setTimeBack(Math.max(0, time - 30));
                    return "Miam ! Ce sandwich est délicieux, tu te sens plein d'énergie ! (Gain de 30 minutes)";
                } else {
                    compteur.setTimeBack(time + 30);
                    return "Beurk... Le sandwich était avarié ! Tu perds du temps à être malade... (Perte de 30 minutes)";
                }
            } else {
                return "Erreur : Impossible d'appliquer l'effet du sandwich (compteur non disponible).";
            }
        }

        // Easter Egg : CHAT GPT
        if (objetTrouve.getLabel().toUpperCase().contains("CHAT GPT")) {
            java.util.Random rand = new java.util.Random();
            int chance = rand.nextInt(100);
            if (chance < 30) {
                return "Chat GPT : \"Je suis désolé, je ne peux pas vous aider avec ça. Je suis juste un modèle de langage.\"";
            } else if (chance < 60) {
                return "Chat GPT : \"Voici une réponse générique qui ne répond pas vraiment à votre question.\"";
            } else if (chance < 90) {
                return "Chat GPT : \"En tant qu'IA, je ne peux pas avoir d'opinion sur ce sujet.\"";
            } else {
                return "Chat GPT : \"*Bip* *Bop* Je suis un robot très intelligent ! *Bip* *Bop*\"";
            }
        }

        // Easter Egg : MASCOTTE
        if (objetTrouve.getLabel().toUpperCase().contains("MASCOTTE")) {
            java.util.Random rand = new java.util.Random();
            int chance = rand.nextInt(100);
            if (chance < 25) {
                return "La mascotte vous fait un clin d'œil et disparaît dans un nuage de confettis !";
            } else if (chance < 50) {
                return "La mascotte fait une danse de la victoire !";
            } else if (chance < 75) {
                return "La mascotte vous donne un high-five !";
            } else {
                return "La mascotte vous fait un câlin réconfortant !";
            }
        }

        return "Vous ne pouvez pas utiliser cet objet pour le moment.";
    }

    /**
     * Définit le pseudo du joueur s'il n'est pas déjà connecté.
     *
     * @param pseudo Nouveau pseudo.
     */
    public void setPseudo(String pseudo) {
        if (!isLogged){
            this.pseudo = pseudo;
            this.isLogged = true;
        }
    }

    /**
     * Récupère le pseudo du joueur.
     *
     * @return Pseudo du joueur.
     */
    public String getPseudo(){
        return this.pseudo;
    }

    /**
     * Vérifie si le joueur est connecté.
     *
     * @return true si connecté, sinon false.
     */
    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Retourne le pNJ actuellement en interaction pour un quiz.
     *
     * @return PNJ en cours.
     */
    public PNJ getTalkingTo() {
        return talkingTo;
    }

    /**
     * Définit le professeur actuellement en interaction.
     *
     * @param pnjTalkingTo :
     */
    public void setTalkingTo(PNJ pnjTalkingTo){
        this.talkingTo = pnjTalkingTo;
    }

    public void setFinalExamStarted(){
         this.isFinalExamStarted = true;
    }

    public boolean getIsFinaleExamStarted(){
        return isFinalExamStarted;
    }

    /**
     * Récupère l'inventaire du joueur.
     *
     * @return Inventaire du joueur.
     */
    public Inventaire getInventaireJoueur() {
        return inventaireJoueur;
    }

    /**
     * Définit un nouvel inventaire pour le joueur.
     *
     * @param inventaireJoueur Inventaire à assigner.
     */
    public void setInventaireJoueur(Inventaire inventaireJoueur) {
        this.inventaireJoueur = inventaireJoueur;
    }

    /**
     * Permet de prendre un objet présent dans la zone.
     *
     * @param nomObjet Nom de l'objet à prendre.
     * @param zone Zone actuelle.
     * @return Message de confirmation ou d'erreur.
     */
    public String prendreObjet(String nomObjet, Zone zone) {
        // Si pas de paramètre et qu'il n'y a qu'un seul objet, on le prend par défaut
        if (nomObjet == null || nomObjet.trim().isEmpty()) {
            List<Objet> objets = zone.getObjets();
            if (objets.size() == 1) {
                Objet objet = objets.get(0);
                nomObjet = objet.getLabel();
            } else {
            return "Que voulez-vous prendre ?";
            }
        }

        Objet objet = zone.retirerObjet(nomObjet);

        if (objet == null) {
            return "Il n'y a pas de '" + nomObjet + "' ici.";
        }

        inventaireJoueur.ajouterObjet(objet);
        return "Vous avez pris : " + objet.getLabel();
    }

    /**
     * Affiche le contenu de l'inventaire du joueur.
     *
     * @return Chaîne représentant les objets de l'inventaire.
     */
    public String afficherInventaire() {
        List<Objet> objets = inventaireJoueur.getObjets();
        StringBuilder sb = new StringBuilder();

        sb.append("=== INVENTAIRE ===\n");
        if (objets.isEmpty()) {
            sb.append("Votre inventaire est vide.\n");
        } else {
            sb.append("Contenu de l'inventaire :\n");
            for (Objet obj : objets) {
                sb.append("- ").append(obj.getLabel());
                if (obj.getIdObjet() != null && !obj.getIdObjet().isEmpty()) {
                    sb.append(" (alias: ").append(obj.getIdObjet()).append(")");
                }
                sb.append("\n");
            }
        }
        sb.append("==================\n");

        return sb.toString();
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    /**
     * Retourne le nombre de téléportations restantes.
     *
     * @return Nombre de téléportations restantes.
     */
    public int getTeleportationsRestantes() {
        return teleportationsRestantes;
    }

    /**
     * Définit le nombre de téléportations restantes.
     *
     * @param teleportationsRestantes Nouveau nombre de téléportations restantes.
     */
    public void setTeleportationsRestantes(int teleportationsRestantes) {
        this.teleportationsRestantes = teleportationsRestantes;
    }

    /**
     * Tente de téléporter le joueur vers une zone.
     *
     * @param zone Zone de destination.
     * @return Message indiquant le résultat de la téléportation.
     */
    public String teleporter(Zone zone) {
        if (teleportationsRestantes > 0) {
            teleportationsRestantes--;
            return "Téléportation réussie vers " + zone.getNom() + " !\n" +
                   "Il vous reste " + teleportationsRestantes + " téléportation(s).";
        } else {
            return "Vous n'avez plus de téléportations disponibles !";
        }
    }

    /**
     * Réinitialise le nombre de téléportations pour une nouvelle course.
     */
    public void reinitialiserTeleportations() {
        teleportationsRestantes = 2;
    }
}


