package jeu.PNJ;

import jeu.Objet;
import jeu.Question;

import java.util.ArrayList;
import java.util.List;

public class PNJ_Prof extends PNJ{
    private List<Question> listeQuestion = new ArrayList<>();
    private boolean hasGivenQuiz;
    private String matiere;
    private final int idQuiz; // ID unique du quiz

    public PNJ_Prof(String unIdPNJ, String unNomPNJ, String[] tabInterractions, Objet objetADonner, String matiere, int idQuiz) {
        super(unIdPNJ, unNomPNJ, tabInterractions, objetADonner);
        this.matiere = matiere;
        this.hasGivenQuiz = false;
        this.idQuiz = idQuiz;
    }

    public String donnerQuiz() {
        if (!hasGivenQuiz) {
            hasGivenQuiz = true;
            return "Bienvenue en " + matiere + " !\n" +
                   "Je vais vous poser quelques questions pour évaluer vos connaissances. \n"+
                    "(Utilisez la commande REPONDRE [réponse] pour répondre !) \n";
        }
        return "Vous avez déjà passé le quiz de " + matiere + " !";
    }

    public int getIdQuiz() {
        return idQuiz;
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
