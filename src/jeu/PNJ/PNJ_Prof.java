package jeu.PNJ;

import jeu.Objet;
import jeu.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PNJ_Prof extends PNJ{
    private List<Question> listeQuestion = new ArrayList<>();
    private boolean hasGivenQuiz;
    private String matiere;
    private String reponseQuestion;
    private Question questionPosee;
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
            Question question = selectRandomQuestion();
            this.questionPosee = question;
            this.reponseQuestion = question.getReponseQuestion();
            return "Bienvenue en " + matiere + " !\n" +
                   "Je vais vous poser quelques questions pour évaluer vos connaissances. \n"+
                    "(Utilisez la commande REPONDRE [réponse] pour répondre !) \n"+
                    question.getTextQuestion();
        }
        return "Vous avez déjà passé le quiz de " + matiere + " !";
    }

    private Question selectRandomQuestion() {
        return listeQuestion.get(new Random().nextInt(0,3));
    }
    public int getIdQuiz() {
        return idQuiz;
    }

    public boolean isAnswerTrue(String reponse) {
        return(reponseQuestion.equalsIgnoreCase(reponse));
    }

    public Question getQuestionPosee(){
        return questionPosee;
    }

    public boolean hasGivenQuiz(){
        return hasGivenQuiz;
    }

    public Objet giveItem(){
        return this.objetADonner;
    }

    public void ajouterQuestion(Question question) {
        listeQuestion.add(question);
    }

    public List<Question> getQuestions() {
        return listeQuestion;
    }
}
