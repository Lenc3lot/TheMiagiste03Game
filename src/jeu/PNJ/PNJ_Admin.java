package jeu.PNJ;

import jeu.Objet;
import java.util.ArrayList;
import java.util.List;

public class PNJ_Admin extends PNJ {
    private boolean hasGivenSchedule;
    private final Objet emploiDuTemps;
    private Boolean isFinalExamSuccessed;
    private final List<Integer> listeQuizs;

    public PNJ_Admin(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.hasGivenSchedule = false;
        this.emploiDuTemps = new Objet("EDT", "Emploi du temps MIAGE", true, true);
        this.listeQuizs = new ArrayList<>();
        // Ajout des IDs des quizs disponibles (les vrais quizs des profs)
        listeQuizs.add(1); // Quiz 1
        listeQuizs.add(2); // Quiz 2
        listeQuizs.add(3); // Quiz 3
        listeQuizs.add(4); // Quiz 4
        listeQuizs.add(5); // Quiz 5
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

    public List<Integer> getListeQuizs() {
        return new ArrayList<>(listeQuizs); // Retourne une copie pour éviter les modifications externes
    }

    public void startExamFinal() {
        // TODO
    }
}

