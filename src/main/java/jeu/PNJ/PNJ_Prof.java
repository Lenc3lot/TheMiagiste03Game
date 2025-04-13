package jeu.PNJ;

import jeu.Question;

import java.util.ArrayList;
import java.util.List;

public class PNJ_Prof extends PNJ{
    private int visibleTime;
    private List<Question> listeQuestion = new ArrayList<>();

    public PNJ_Prof(String unIdPNJ, String unNomPNJ, String[] tabInterractions, int visibleTime) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.visibleTime = visibleTime;
    }

    public boolean isAnswerTrue(String reponse){
        // TODO : Renvoie vrai si la réponse à la question correspond
        return true;
    }
}
