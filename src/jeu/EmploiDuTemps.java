package jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class EmploiDuTemps extends Objet implements Serializable {
    private final List<Integer> quizs; 
    private int quizActuel; 
    private boolean estValide; 
    private static final Map<Integer, String[]> QUIZ_INFO = new HashMap<>();
    static {
        QUIZ_INFO.put(1, new String[]{"Transformation numérique", "Salle 1.01"});
        QUIZ_INFO.put(2, new String[]{"Gestion de la qualité", "Salle 0.01"});
        QUIZ_INFO.put(3, new String[]{"Anglais", "Salle 2.01"});
        QUIZ_INFO.put(4, new String[]{"Algorithmique", "Salle 2.04"});
        QUIZ_INFO.put(5, new String[]{"Gestion de projet SCRUM", "Salle 2.18"});
        QUIZ_INFO.put(6, new String[]{"Utilisation de Wooclap", "Salle 2.18"});
    }
    
    public EmploiDuTemps(List<Integer> listeQuizs) {
        super("EMPLOI_DU_TEMPS", "Emploi du temps MIAGE", true, true, "Votre emploi du temps de la MIAGE");
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
    
    public boolean peutFaireQuiz(int idQuiz) {
        return estValide && quizActuel < quizs.size() && quizs.get(quizActuel) == idQuiz;
    }
    
    public void passerAuQuizSuivant() {
        if (quizActuel < quizs.size() - 1) {
            quizActuel++;
        }
    }
    
    public void validerEmploiDuTemps() {
        this.estValide = true;
    }
    
    public boolean estValide() {
        return estValide;
    }
    
    public int getQuizActuel() {
        return quizs.get(quizActuel);
    }
    
    public boolean estDernierQuiz() {
        return quizActuel == quizs.size() - 1;
    }
    
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
            if (i == quizActuel) {
                sb.append("→ ");
            } else {
                sb.append("  ");
            }
            
            sb.append(String.format("%-30s", info[0]));
            sb.append(" ║ ");
            sb.append(String.format("%-10s", info[1]));
            
            if (i < quizActuel) {
                sb.append(" ✓");
            }
            sb.append("\n");
        }
        
        sb.append("╚══════════════════════════════════════════╝\n");
        return sb.toString();
    }
} 