package jeu;

import java.io.Serializable;

/**
 * Classe représentant une question de quiz posée au joueur dans le jeu.
 *
 * Chaque question possède un identifiant unique, un texte d'énoncé et une réponse correcte associée.
 *
 * Cette classe est sérialisable pour permettre la sauvegarde de l'état des quiz.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class Question implements Serializable {

    /** Identifiant unique de la question. */
    private final int idQuestion;

    /** Texte de la question posée au joueur. */
    private final String textQuestion;

    /** Réponse correcte associée à la question. */
    private final String reponseQuestion;

    /**
     * Constructeur de la classe Question.
     *
     * @param idQuestion Identifiant de la question.
     * @param textQuestion Texte de la question.
     * @param reponseQuestion Réponse correcte à la question.
     */
    public Question(int idQuestion, String textQuestion, String reponseQuestion) {
        this.idQuestion = idQuestion;
        this.textQuestion = textQuestion;
        this.reponseQuestion = reponseQuestion;
    }

    /**
     * Retourne le texte de la question.
     *
     * @return Texte de la question.
     */
    public String getTextQuestion() {
        return textQuestion;
    }

    /**
     * Retourne la réponse correcte de la question.
     *
     * @return Réponse attendue.
     */
    public String getReponseQuestion() {
        return reponseQuestion;
    }
}
