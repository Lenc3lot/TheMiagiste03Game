package jeu.PNJ;

import jeu.Objet;

public class PNJ_ZamZam extends PNJ {
    private boolean hasGivenSandwich;
    private final Objet sandwich;

    public PNJ_ZamZam(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.hasGivenSandwich = false;
        this.sandwich = new Objet("SANDWICH", "Magnifique sandwich du ZAM ZAM", false, true);
    }

    public Objet donnerSandwich() {
        if (!hasGivenSandwich) {
            hasGivenSandwich = true;
            return sandwich;
        }
        return null;
    }

    public Objet getSandwich() {
        return sandwich;
    }
} 