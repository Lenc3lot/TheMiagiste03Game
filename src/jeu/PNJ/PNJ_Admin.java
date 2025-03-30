package jeu.PNJ;

public class PNJ_Admin extends PNJ{
    private Boolean isFinalExamSuccessed;

    public PNJ_Admin(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
    }

    public boolean isAllKeyItemsCollected(){
        // TODO : Verifie si le nombre d'item clés ont étés collectés avant l'heure max
    }

    /**
     * Lance l'exam final si #isAllKeyItemsCollected() renvoie true.
     */
    public void startExamFinal(){
        // TODO
    }
}
