package jeu;

import jeu.PNJ.*;
import java.util.List;

public class Joueur {
    private String pseudo;
    private Inventaire inventaireJoueur;
    private boolean isLogged;
    private Zone zoneCourante;

    public Joueur(String unPseudo) {
        this.pseudo = unPseudo;
        this.inventaireJoueur = new Inventaire();
        this.isLogged = false;
    }

    public void setZoneCourante(Zone zone) {
        this.zoneCourante = zone;
    }

    public String parler(String personnage) {
        if (personnage == null || personnage.trim().isEmpty()) {
            return "A qui voulez-vous parler ?";
        }

        List<PNJ> pnjs = zoneCourante.getPNJs();
        if (pnjs.isEmpty()) {
            return "Il n'y a personne ici à qui parler.";
        }

        for (PNJ pnj : pnjs) {
            if (pnj.getNomPNJ().equalsIgnoreCase(personnage)) {
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
                        zoneCourante.changerImage(zoneCourante.nomImage().replace("Avec", "Sans"));
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
                        zoneCourante.changerImage(zoneCourante.nomImage().replace("Avec", "Sans"));
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

    public void prendre(String objet) {
        // Récupère un objet
    }

    public String inspecter(String element) {
        // TODO : Doit retourner une string qui dit si la zone contient ou non un element a ouvrir ou prendre
        return "";
    }

    public String ouvrir(String conteneur) {
        //TODO : Doit retourner une string qui dit que si quelque-chose est dans l'élément ouvert
        return "";
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

    public String prendreObjet(String nomObjet) {
        if (nomObjet == null || nomObjet.trim().isEmpty()) {
            return "Que voulez-vous prendre ?";
        }
        
        Objet objet = zoneCourante.retirerObjet(nomObjet);
        
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


