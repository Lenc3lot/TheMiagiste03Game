package jeu;

import java.io.Serializable;
import jeu.PNJ.*;
import java.util.List;
public class    Joueur  implements Serializable{
    private String pseudo;
    private Inventaire inventaireJoueur;
    private boolean isLogged;
    private PNJ_Prof talkingTo;
    private boolean aRencontreSin;
    private boolean aRencontreSandrine;
    private boolean estEtudiant;
    private transient Jeu jeu;

    public Joueur(String unPseudo) {
        this.pseudo = unPseudo;
        this.inventaireJoueur = new Inventaire();
        this.isLogged = false;
        this.aRencontreSin = false;
        this.aRencontreSandrine = false;
        this.estEtudiant = false;
    }

    public String parler(String personnage, Zone zone) {
        if (personnage == null || personnage.trim().isEmpty()) {
            return "A qui voulez-vous parler ?";
        }

        List<PNJ> pnjs = zone.getPNJs();
        if (pnjs.isEmpty()) {
            return "Il n'y a personne ici à qui parler.";
        }

        personnage = personnage.trim().toLowerCase();
        for (PNJ pnj : pnjs) {
            String[] pngId = pnj.getNomPNJ().split(" ",2);
            if (pngId[0].toLowerCase().equals(personnage)) {
                if (!estEtudiant && !personnage.equals("sin") && !personnage.equals("sandrine")) {
                    return "Vous n'êtes pas encore un étudiant de la MIAGE. Vous devez d'abord rencontrer SIN puis SANDRINE.";
                }

                if (pnj instanceof PNJ_Guide) {
                    PNJ_Guide guide = (PNJ_Guide) pnj;
                    if (personnage.equals("sin")) {
                        aRencontreSin = true;
                    }
                    return guide.donnerIndice();
                } else if (pnj instanceof PNJ_Admin) {
                    PNJ_Admin admin = (PNJ_Admin) pnj;
                    if (personnage.equals("sin")) {
                        aRencontreSin = true;
                        return "SIN : Bienvenue à la MIAGE ! Vous devez maintenant aller voir SANDRINE pour obtenir votre emploi du temps.";
                    } else if (personnage.equals("sandrine")) {
                        if (!aRencontreSin) {
                            return "SANDRINE : Désolée, mais vous devez d'abord rencontrer SIN avant de venir me voir.";
                        }
                        aRencontreSandrine = true;
                        estEtudiant = true;

                        // Création de l'emploi du temps
                        List<Integer> listeQuizs = admin.getListeQuizs();
                        EmploiDuTemps emploiDuTemps = new EmploiDuTemps(listeQuizs);
                        emploiDuTemps.validerEmploiDuTemps();

                        inventaireJoueur.ajouterObjet(emploiDuTemps);
                        zone.changerImage(zone.nomImage().replace("Avec", "Sans"));

                        return "SANDRINE : Voici votre emploi du temps. Consultez-le régulièrement pour savoir quels quizs vous devez faire.";
                    }
                    return admin.donnerEmploiDuTemps();
                } else if (pnj instanceof PNJ_ZamZam) {
                    int minutes = 0;
                    int heures = 0;
                    if (jeu != null && jeu.getCompteur() != null) {
                        int time = jeu.getCompteur().getTimeLeft();
                        heures = ((time % 86400) % 3600) / 60;
                        minutes = ((time % 86400) % 3600) % 60;
                    }
                    if (heures < 11 || heures >= 12) {
                        return "Le chef du ZAM ZAM n'est disponible que de 11h à 12h ! (Heure actuelle : " + heures + "h" + String.format("%02d", minutes) + ")";
                    }
                    PNJ_ZamZam zamZam = (PNJ_ZamZam) pnj;
                    Objet sandwich = zamZam.donnerSandwich();
                    if (sandwich != null) {
                        inventaireJoueur.ajouterObjet(sandwich);
                        zone.changerImage(zone.nomImage().replace("Avec", "Sans"));
                        return "Voici un magnifique sandwich pour vous donner des forces !\nVous pourrez l'utiliser quand vous en aurez besoin.\nVous avez reçu : " + sandwich.getLabel();
                    } else {
                        return "Vous avez déjà reçu votre sandwich !";
                    }
                } else if (pnj instanceof PNJ_Prof prof) {
                    EmploiDuTemps emploiDuTemps = getEmploiDuTemps();
                    if (emploiDuTemps == null) {
                        return "Vous devez d'abord obtenir votre emploi du temps chez SANDRINE.";
                    }
                    if (!emploiDuTemps.estValide()) {
                        return "Votre emploi du temps n'est pas encore valide.";
                    }
                    if (!emploiDuTemps.peutFaireQuiz(prof.getIdQuiz())) {
                        return "Ce n'est pas le moment de faire ce quiz. Consultez votre emploi du temps.";
                    }
                    if(!prof.hasGivenQuiz()){
                        setTalkingTo(prof);
                    }
                    return prof.donnerQuiz();
                } else {
                    StringBuilder message = new StringBuilder();
                    for (String dialogue : pnj.getTexteInterraction()) {
                        message.append(dialogue).append("\n");
                    }
                    return message.toString();
                }
            }
        }

        return "La personne que vous cherchez n'est pas ici.";
    }

    public EmploiDuTemps getEmploiDuTemps() {
        List<Objet> objets = inventaireJoueur.getObjets();
        for (Objet obj : objets) {
            if (obj instanceof EmploiDuTemps) {
                return (EmploiDuTemps) obj;
            }
        }
        return null;
    }

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

    public String utiliser(String objet) {
        if (objet == null || objet.trim().isEmpty()) {
            return "Quel objet voulez-vous utiliser ?";
        }

        objet = objet.trim().toUpperCase();
        List<Objet> objets = inventaireJoueur.getObjets();

        for (Objet obj : objets) {
            if (obj.getLabel().toUpperCase().contains(objet) || objet.contains(obj.getLabel().toUpperCase())) {
                if (obj instanceof EmploiDuTemps) {
                    return obj.toString();
                }
                // Gestion spéciale pour le sandwich du ZAM ZAM
                if (obj.getLabel().toUpperCase().contains("SANDWICH")) {
                    java.util.Random rand = new java.util.Random();
                    boolean bon = rand.nextBoolean();
                    inventaireJoueur.retirerObjet(obj); // On retire le sandwich après usage
                    if (jeu != null && jeu.getCompteur() != null) {
                        int time = jeu.getCompteur().getTimeLeft();
                        if (bon) {
                            jeu.getCompteur().setTimeBack(Math.max(0, time - 30));
                            return "Miam ! Ce sandwich est délicieux, tu te sens plein d'énergie ! (Gain de 30 minutes)";
                        } else {
                            jeu.getCompteur().setTimeBack(time + 30);
                            return "Beurk... Le sandwich était avarié ! Tu perds du temps à être malade... (Perte de 30 minutes)";
                        }
                    } else {
                        return "Erreur : Impossible d'appliquer l'effet du sandwich (compteur non disponible).";
                    }
                }
                return "Vous ne pouvez pas utiliser cet objet pour le moment.";
            }
        }

        return "Vous n'avez pas cet objet dans votre inventaire.";
    }

    public void setPseudo(String pseudo) {
        if (!isLogged){
            this.pseudo = pseudo;
            this.isLogged = true;
        }
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public PNJ_Prof getTalkingTo() {
        return talkingTo;
    }

    public void setTalkingTo(PNJ_Prof pnjProf){
        this.talkingTo = pnjProf;
    }

    public Inventaire getInventaireJoueur() {
        return inventaireJoueur;
    }

    public void setInventaireJoueur(Inventaire inventaireJoueur) {
        this.inventaireJoueur = inventaireJoueur;
    }

    public String prendreObjet(String nomObjet, Zone zone) {
        // Si pas de paramètre et qu'il n'y a qu'un seul objet, on le prend par défaut
        if (nomObjet == null || nomObjet.trim().isEmpty()) {
            List<Objet> objets = zone.getObjets();
            if (objets.size() == 1) {
                Objet objet = objets.get(0);
                zone.retirerObjet(objet.getLabel());
                inventaireJoueur.ajouterObjet(objet);
                return "Vous avez pris : " + objet.getLabel();
            }
            return "Que voulez-vous prendre ?";
        }

        Objet objet = zone.retirerObjet(nomObjet);

        if (objet == null) {
            return "Il n'y a pas de '" + nomObjet + "' ici.";
        }

        inventaireJoueur.ajouterObjet(objet);
        return "Vous avez pris : " + objet.getLabel();
    }

    public String afficherInventaire() {
        List<Objet> objets = inventaireJoueur.getObjets();
        StringBuilder sb = new StringBuilder();

        sb.append("=== INVENTAIRE ===\n");
        if (objets.isEmpty()) {
            sb.append("Votre inventaire est vide.\n");
        } else {
            sb.append("Contenu de l'inventaire :\n");
            for (Objet obj : objets) {
                sb.append("- ").append(obj.getLabel()).append("\n");
            }
        }
        sb.append("==================\n");

        return sb.toString();
    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }
}


