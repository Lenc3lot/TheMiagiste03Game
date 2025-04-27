package jeu.PNJ;

import jeu.Objet;

/**
 * Classe représentant un PNJ du restaurant ZAM ZAM dans le jeu.
 *
 * Ce personnage peut donner un sandwich au joueur, mais uniquement une seule fois.
 * Le sandwich est un objet spécial pouvant influencer la partie (ex: gain de bonus).
 *
 * @see PNJ
 * @see Objet
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class PNJ_ZamZam extends PNJ {

    /** Indique si le sandwich a déjà été donné au joueur. */
    private boolean hasGivenSandwich;

    /** Objet représentant le sandwich donné par le PNJ. */
    private final Objet sandwich;

    /**
     * Constructeur du PNJ ZamZam.
     *
     * @param unIdPNJ Identifiant unique du PNJ.
     * @param unNomPNJ Nom du PNJ.
     * @param tabInterractions Tableau des interactions possibles.
     */
    public PNJ_ZamZam(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.hasGivenSandwich = false;
        this.sandwich = new Objet("SANDWICH", "Magnifique sandwich du ZAM ZAM", false, true);
    }

    /**
     * Donne le sandwich au joueur s'il n'a pas encore été donné.
     *
     * @return Sandwich si disponible, null sinon.
     */
    public Objet donnerSandwich() {
        if (!hasGivenSandwich) {
            hasGivenSandwich = true;
            return sandwich;
        }
        return null;
    }

    /**
     * Retourne le sandwich associé au PNJ.
     *
     * @return Objet représentant le sandwich.
     */
    public Objet getSandwich() {
        return sandwich;
    }
} 