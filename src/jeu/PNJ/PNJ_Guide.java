package jeu.PNJ;

public class PNJ_Guide extends PNJ {
    private boolean hasGivenInitialHint;
    private String[] initialHints = {
        "SALAM ! Bienvenue à Forbin ! Je suis SIN, votre guide pour cette aventure.",
        "Votre objectif est de réussir votre année de MIAGE.",
        "Commencez par aller voir Sandrine au bureau d'administration pour obtenir votre emploi du temps.",
        "N'oubliez pas de passer au ZAM ZAM pour prendre des forces !"
    };

    public PNJ_Guide(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.hasGivenInitialHint = false;
    }

    public String donnerIndice() {
        if (!hasGivenInitialHint) {
            hasGivenInitialHint = true;
            return initialHints[0] + "\n" + initialHints[1] + "\n" + initialHints[2] + "\n" + initialHints[3];
        }
        return "Vous êtes sur la bonne voie ! Continuez à explorer Forbin.";
    }
} 