package jeu;

import java.io.Serializable;
import jeu.PNJ.*;
import java.util.List;
public class Joueur  implements Serializable{
    private String pseudo;
    private Inventaire inventaireJoueur;
    private boolean isLogged;

    public Joueur(String unPseudo) {
        this.pseudo = unPseudo;
        this.inventaireJoueur = new Inventaire();
        this.isLogged = false;
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
                // Gestion des différents types de PNJ
                if (pnj instanceof PNJ_Guide) {
                    PNJ_Guide guide = (PNJ_Guide) pnj;
                    return guide.donnerIndice();
                } else if (pnj instanceof PNJ_Admin) {
                    PNJ_Admin admin = (PNJ_Admin) pnj;
                    String message = admin.donnerEmploiDuTemps();
                    Objet emploiDuTemps = admin.getEmploiDuTemps();
                    if (emploiDuTemps != null) {
                        inventaireJoueur.ajouterObjet(emploiDuTemps);
                        message += "\nVous avez reçu : " + emploiDuTemps.getLabel();
                        // Changer l'image de la zone après avoir reçu l'objet
                        zone.changerImage(zone.nomImage().replace("Avec", "Sans"));
                    }
                    return message;
                } else if (pnj instanceof PNJ_ZamZam) {
                    PNJ_ZamZam zamZam = (PNJ_ZamZam) pnj;
                    String message = zamZam.donnerSandwich();
                    Objet sandwich = zamZam.getSandwich();
                    if (sandwich != null) {
                        inventaireJoueur.ajouterObjet(sandwich);
                        message += "\nVous avez reçu : " + sandwich.getLabel();
                        // Changer l'image de la zone après avoir reçu l'objet
                        zone.changerImage(zone.nomImage().replace("Avec", "Sans"));
                    }
                    return message;
                } else if (pnj instanceof PNJ_Prof) {
                    PNJ_Prof prof = (PNJ_Prof) pnj;
                    return prof.donnerQuiz();
                } else {
                    // Pour les autres PNJ, afficher leurs dialogues de base
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
        //TODO : Retourne une string liée à l'objet
        return "";
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
}


