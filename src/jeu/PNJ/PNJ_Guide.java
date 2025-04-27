package jeu.PNJ;

/**
 * Classe représentant un PNJ (Personnage Non Joueur) guide dans le jeu.
 *
 * Le guide fournit des indices initiaux au joueur pour l'aider à débuter son aventure
 * et l'oriente vers les premières étapes importantes.
 *
 * Ce PNJ spécial s'appelle "SIN" dans le contexte du jeu "THE MIAGISTE 03".
 *
 * @see PNJ
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class PNJ_Guide extends PNJ {

    /** Indique si le guide a déjà donné les indices initiaux. */
    private boolean hasGivenInitialHint;

    /** Tableau contenant les messages d'indices initiaux. */
    private String[] initialHints = {
        "Bienvenue à Forbin ! Je suis SIN, votre guide pour cette aventure.",
        "Votre objectif est de réussir votre année de MIAGE.",
        "Commencez par aller voir Sandrine au bureau d'administration pour obtenir votre emploi du temps.",
        "N'oubliez pas de passer au ZAM ZAM pour prendre des forces !"
    };

    /**
     * Constructeur du PNJ Guide.
     *
     * @param unIdPNJ Identifiant unique du PNJ.
     * @param unNomPNJ Nom du PNJ.
     * @param tabInterractions Tableau des interactions possibles avec le joueur.
     */
    public PNJ_Guide(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.hasGivenInitialHint = false;
    }

    /**
     * Retourne un indice pour aider le joueur.
     *
     * Lors du premier appel, plusieurs indices initiaux sont donnés.
     * Lors des appels suivants, un message d'encouragement est retourné.
     *
     * @return Message d'indice ou d'encouragement.
     */
    public String donnerIndice() {
        if (!hasGivenInitialHint) {
            hasGivenInitialHint = true;
            return initialHints[0] + "\n" + initialHints[1] + "\n" + initialHints[2] + "\n" + initialHints[3];
        }
        return "Vous êtes sur la bonne voie ! Continuez à explorer Forbin.";
    }
} 