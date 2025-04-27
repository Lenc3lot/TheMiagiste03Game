package jeu.PNJ;

import jeu.Objet;

import java.io.Serializable;

/**
 * Classe abstraite représentant un Personnage Non Joueur (PNJ) dans le jeu.
 *
 * Un PNJ possède un identifiant, un nom, des textes d'interaction et peut éventuellement donner un objet au joueur.
 *
 * Tous les types spécifiques de PNJ (Admin, Guide, Professeur, ZamZam) hériteront de cette classe.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
abstract public class PNJ implements Serializable {
    /** Identifiant unique du PNJ. */
    String idPNJ;
    /** Nom du PNJ affiché dans le jeu. */
    String nomPNJ;

    /** Textes d'interaction disponibles pour le joueur. */
    String[] texteInterraction;

    /** Objet que le PNJ peut donner au joueur (optionnel). */
    Objet objetADonner;

    /**
     * Constructeur de PNJ sans objet à donner.
     *
     * @param unIdPNJ Identifiant du PNJ.
     * @param unNomPNJ Nom du PNJ.
     * @param tabInterractions Textes d'interaction possibles.
     */
    public PNJ(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        this.idPNJ = unIdPNJ;
        this.nomPNJ = unNomPNJ;
        this.texteInterraction = tabInterractions;
    }

    /**
     * Constructeur de PNJ avec un objet à donner.
     *
     * @param unIdPNJ Identifiant du PNJ.
     * @param unNomPNJ Nom du PNJ.
     * @param tabInterractions Textes d'interaction possibles.
     * @param objetADonner Objet que le PNJ pourra donner.
     */
    public PNJ(String unIdPNJ, String unNomPNJ, String[] tabInterractions, Objet objetADonner) {
        this.idPNJ = unIdPNJ;
        this.nomPNJ = unNomPNJ;
        this.texteInterraction = tabInterractions;
        this.objetADonner = objetADonner;
    }

    /**
     * Donne un objet au joueur et affiche un message associé.
     *
     * @return Message indiquant l'objet donné.
     */
     public String donnerObjet(){
        // TODO : Donne un objet et affiche un message
         return "Voici " + objetADonner.getLabel();
     }

    /**
     * Retourne le nom du PNJ.
     *
     * @return Nom du PNJ.
     */
    public String getNomPNJ() {
        return nomPNJ;
    }

    /**
     * Retourne l'identifiant du PNJ.
     *
     * @return Identifiant du PNJ.
     */
    public String getIdPNJ() {
        return idPNJ;
    }

    /**
     * Retourne les textes d'interaction associés au PNJ.
     *
     * @return Tableau de textes d'interaction.
     */
    public String[] getTexteInterraction() {
        return texteInterraction;
    }
}
