package jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

/**
 * Classe représentant l'emploi du temps du joueur dans le jeu.
 * L'emploi du temps guide le joueur à travers les différents quiz à accomplir pour progresser.
 *
 * L'objet EmploiDuTemps doit être validé avant de pouvoir effectuer les quiz.
 * Chaque quiz correspond à une matière et une salle spécifique.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */

public class EmploiDuTemps extends Objet implements Serializable {

    /** Liste des identifiants des quiz à accomplir. */
    private final List<Integer> quizs;

    /** Indice du quiz actuel dans la liste. */
    private int quizActuel;

    /** Indique si l'emploi du temps est valide (prêt à être utilisé). */
    private boolean estValide;

    /** Informations associées aux quiz : matière et salle. */
    private static final Map<Integer, String[]> QUIZ_INFO = new HashMap<>();

    // Initialisation statique des matières et des salles
    static {
        QUIZ_INFO.put(1, new String[]{"Transformation numérique", "Salle 1.01"});
        QUIZ_INFO.put(2, new String[]{"Gestion de la qualité", "Salle 0.01"});
        QUIZ_INFO.put(3, new String[]{"Anglais", "Salle 2.01"});
        QUIZ_INFO.put(4, new String[]{"Algorithmique", "Salle 2.04"});
        QUIZ_INFO.put(5, new String[]{"Gestion de projet SCRUM", "Salle 2.18"});
        QUIZ_INFO.put(6, new String[]{"Utilisation de Wooclap", "Salle 2.18"});
    }

    /**
     * Constructeur de l'emploi du temps.
     * Mélange les quizs.
     *
     * @param listeQuizs Liste initiale des identifiants des quiz.
     */
    public EmploiDuTemps(List<Integer> listeQuizs) {
        super("EDT", "Emploi du temps MIAGE", false, true, "Votre emploi du temps de la MIAGE");
        this.quizs = new ArrayList<>(listeQuizs);
        if (!quizs.isEmpty()) {
            int premierQuiz = quizs.get(0);
            quizs.remove(0);
            Collections.shuffle(quizs, new Random(System.currentTimeMillis()));
            quizs.add(0, premierQuiz);
        }
        this.quizActuel = 0;
        this.estValide = false;
    }

    /**
     * Vérifie si le joueur peut effectuer un quiz donné.
     *
     * @param idQuiz Identifiant du quiz.
     * @return true si l'emploi du temps est valide et que c'est le bon quiz à faire, false sinon.
     */
    public boolean peutFaireQuiz(int idQuiz) {
        return estValide && quizActuel < quizs.size() && quizs.get(quizActuel) == idQuiz;
    }

    /**
     * Passe au quiz suivant dans l'emploi du temps.
     */
    public void passerAuQuizSuivant() {
        quizActuel++;
    }

    /**
     * Valide l'emploi du temps, le rendant utilisable.
     */
    public void validerEmploiDuTemps() {
        this.estValide = true;
    }

    /**
     * Vérifie si l'emploi du temps est valide.
     *
     * @return true si l'emploi du temps est validé, false sinon.
     */
    public boolean estValide() {
        return estValide;
    }

    /**
     * Obtient l'identifiant du quiz actuel.
     *
     * @return Identifiant du quiz actuel.
     */
    public int getQuizActuel() {
        return quizs.get(quizActuel);
    }

    /**
     * Vérifie si le joueur a terminé tous les quiz de l'emploi du temps.
     *
     * @return true si tous les quiz sont terminés, false sinon.
     */
    public boolean estDernierQuiz() {
        return quizActuel >= quizs.size() - 1;
    }

    /**
     * Retourne une représentation textuelle de l'emploi du temps.
     *
     * @return Chaîne représentant l'emploi du temps avec les quiz et salles.
     */
    @Override
    public String toString() {
        if (!estValide) {
            return "Vous n'avez pas encore d'emploi du temps valide.";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════╗\n");
        sb.append("║           EMPLOI DU TEMPS MIAGE          ║\n");
        sb.append("╠══════════════════════════════════════════╣\n");
        
        for (int i = 0; i < quizs.size(); i++) {
            int quizId = quizs.get(i);
            String[] info = QUIZ_INFO.get(quizId);
            
            sb.append("║ ");
            if (i == quizActuel && quizActuel < quizs.size()) {
                sb.append("→ ");
            } else {
                sb.append("  ");
            }
            
            sb.append(String.format("%-30s", info[0]));
            sb.append(" ║ ");
            sb.append(String.format("%-10s", info[1]));
            
            if (i < quizActuel || (i == quizActuel && quizActuel >= quizs.size())) {
                sb.append(" VALIDE");
            }
            sb.append("\n");
        }
        
        sb.append("╚══════════════════════════════════════════╝\n");
        
        // Ajout du message pour aller voir Sandrine après le dernier quiz
        if (quizActuel >= quizs.size()) {
            sb.append("\nFélicitations ! Vous avez terminé tous vos quiz.\n");
            sb.append("Allez voir SANDRINE pour passer l'examen final !\n");
        }
        
        return sb.toString();
    }
} 