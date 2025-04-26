package jeu.PNJ;

import jeu.Question;

import java.util.ArrayList;
import java.util.List;

public class PNJ_Prof extends PNJ{
    private int visibleTime;
    private List<Question> listeQuestion = new ArrayList<>();
    private boolean hasGivenQuiz;
    private String matiere;

    public PNJ_Prof(String unIdPNJ, String unNomPNJ, String[] tabInterractions, int visibleTime, String matiere) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.visibleTime = visibleTime;
        this.matiere = matiere;
        this.hasGivenQuiz = false;
    }

    public String donnerQuiz() {
        if (!hasGivenQuiz) {
            hasGivenQuiz = true;
            return "Bienvenue en " + matiere + " !\n" +
                   "Je vais vous poser quelques questions pour évaluer vos connaissances.";
        }
        return "Vous avez déjà passé le quiz de " + matiere + " !";
    }

    public boolean isAnswerTrue(String reponse) {
        for (Question question : listeQuestion) {
            if (question.getReponseQuestion().equalsIgnoreCase(reponse)) {
                return true;
            }
        }
        return false;
    }

    public void ajouterQuestion(Question question) {
        listeQuestion.add(question);
    }

    public List<Question> getQuestions() {
        return listeQuestion;
    }
}
