package jeu.PNJ;

public class PNJ_Autre extends PNJ{
    private Boolean isAvaible;
    public PNJ_Autre(String unIdPNJ,Boolean isAvaible, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.isAvaible = isAvaible;
    }

    public Boolean asBeenVisited(){
        // RETURN TRUE SI VISITE DEJA EFFECTUEE
        return false;
    }

    // GET AND SET

    public void setAvaible(Boolean avaible) {
        isAvaible = avaible;
    }
}
