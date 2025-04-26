package jeu.PNJ;

import jeu.Objet;

public class PNJ_Admin extends PNJ {
    private boolean hasGivenSchedule;
    private final Objet emploiDuTemps;
    private Boolean isFinalExamSuccessed;

    public PNJ_Admin(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.hasGivenSchedule = false;
        this.emploiDuTemps = new Objet("EDT", "Emploi du temps MIAGE", true, true);
    }

    public boolean isAllKeyItemsCollected(){
        // TODO : Verifie si le nombre d'item clés ont étés collectés avant l'heure max
        return false;
    }

    public String donnerEmploiDuTemps() {
        if (!hasGivenSchedule) {
            hasGivenSchedule = true;
            return "Félicitations ! Voici votre emploi du temps pour l'année.\n" +
                   "Vous devez suivre tous vos cours et passer les examens.\n" +
                   "N'oubliez pas de vous rendre dans les salles indiquées.";
        }
        return "Vous avez déjà votre emploi du temps !";
    }

    public Objet getEmploiDuTemps() {
        return emploiDuTemps;
    }
    public void startExamFinal(){//TODO}
    }
}

