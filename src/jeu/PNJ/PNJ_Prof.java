package jeu.PNJ;

import jeu.Objet;
import jeu.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe représentant un professeur (PNJ) dans le jeu.
 *
 * Chaque professeur propose un quiz lié à une matière spécifique. Le joueur doit répondre
 * correctement aux questions pour obtenir un objet clé.
 *
 * Ce PNJ ne peut poser son quiz qu'une seule fois au joueur.
 *
 * @see PNJ
 * @see Question
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */

public class PNJ_Prof extends PNJ{

    /** Liste des questions du professeur. */
    private List<Question> listeQuestion = new ArrayList<>();

    /** Indique si le quiz a déjà été donné au joueur. */
    private boolean hasGivenQuiz;

    /** Matière enseignée par ce professeur. */
    private String matiere;

    /** Réponse attendue pour la question posée. */
    private String reponseQuestion;

    /** Question actuellement posée au joueur. */
    private Question questionPosee;

    /** Identifiant unique du quiz. */
    private final int idQuiz; // ID unique du quiz

    /**
     * Constructeur du professeur.
     *
     * @param unIdPNJ Identifiant unique du PNJ.
     * @param unNomPNJ Nom du professeur.
     * @param tabInterractions Tableau d'interactions possibles.
     * @param objetADonner Objet donné au joueur après réussite du quiz.
     * @param matiere Matière du quiz.
     * @param idQuiz Identifiant unique du quiz.
     */
    public PNJ_Prof(String unIdPNJ, String unNomPNJ, String[] tabInterractions, Objet objetADonner, String matiere, int idQuiz) {
        super(unIdPNJ, unNomPNJ, tabInterractions, objetADonner);
        this.matiere = matiere;
        this.hasGivenQuiz = false;
        this.idQuiz = idQuiz;
    }

    /**
     * Propose le quiz au joueur.
     *
     * @return Texte du quiz ou un message indiquant que le quiz a déjà été passé.
     */
    public String donnerQuiz() {
        if (!hasGivenQuiz) {
            hasGivenQuiz = true;
            Question question = selectRandomQuestion();
            this.questionPosee = question;
            this.reponseQuestion = question.getReponseQuestion();
            return "Bienvenue en " + matiere + " !\n" +
                   "Je vais vous poser quelques questions pour évaluer vos connaissances. \n"+
                    question.getTextQuestion();
        }
        return "Vous avez déjà passé le quiz de " + matiere + " !";
    }

    /**
     * Sélectionne aléatoirement une question dans la liste des questions disponibles.
     *
     * @return Une question choisie au hasard.
     */
    private Question selectRandomQuestion() {
        return listeQuestion.get(new Random().nextInt(0, 3));
    }

    /**
     * Retourne l'identifiant unique du quiz.
     *
     * @return Identifiant du quiz.
     */
    public int getIdQuiz() {
        return idQuiz;
    }

    /**
     * Vérifie si la réponse du joueur est correcte.
     *
     * @param reponse Réponse donnée par le joueur.
     * @return true si la réponse est correcte, false sinon.
     */
    public boolean isAnswerTrue(String reponse) {
        return reponseQuestion.equalsIgnoreCase(reponse);
    }

    /**
     * Retourne la question actuellement posée.
     *
     * @return Question en cours.
     */
    public Question getQuestionPosee() {
        return questionPosee;
    }

    /**
     * Vérifie si le professeur a déjà donné son quiz.
     *
     * @return true si le quiz a été donné, false sinon.
     */
    public boolean hasGivenQuiz() {
        return hasGivenQuiz;
    }

    /**
     * Retourne l'objet à donner au joueur après réussite du quiz.
     *
     * @return Objet à donner.
     */
    public Objet giveItem() {
        return this.objetADonner;
    }

    /**
     * Ajoute une question à la liste des questions du professeur.
     *
     * @param question Question à ajouter.
     */
    public void ajouterQuestion(Question question) {
        listeQuestion.add(question);
    }

    /**
     * Retourne la liste des questions disponibles pour ce professeur.
     *
     * @return Liste des questions.
     */
    public List<Question> getQuestions() {
        return listeQuestion;
    }
}
