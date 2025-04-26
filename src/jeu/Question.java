package jeu;

import java.io.Serializable;

public class Question implements Serializable {
    private final int idQuestion;
    private final String textQuestion;
    private final String reponseQuestion;

    public Question(int idQuestion,String textQuestion,String reponseQuestion){
        this.idQuestion = idQuestion;
        this.textQuestion = textQuestion;
        this.reponseQuestion = reponseQuestion;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public String getReponseQuestion() {
        return reponseQuestion;
    }
}
