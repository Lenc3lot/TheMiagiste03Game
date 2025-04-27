package test.jeu;

import jeu.EmploiDuTemps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class EmploiDuTempsTest {
    private EmploiDuTemps emploiDuTemps;
    private List<Integer> quizs;

    @BeforeEach
    public void setUp() {
        quizs = new ArrayList<>();
        quizs.add(1);
        quizs.add(2);
        quizs.add(3);
        emploiDuTemps = new EmploiDuTemps(quizs);
    }

    @Test
    public void testConstructeur() {
        assertFalse(emploiDuTemps.estValide());
        assertEquals(1, emploiDuTemps.getQuizActuel());
    }

    @Test
    public void testValiderEmploiDuTemps() {
        emploiDuTemps.validerEmploiDuTemps();
        assertTrue(emploiDuTemps.estValide());
    }

    @Test
    public void testPeutFaireQuiz() {
        assertFalse(emploiDuTemps.peutFaireQuiz(1));
        emploiDuTemps.validerEmploiDuTemps();
        assertTrue(emploiDuTemps.peutFaireQuiz(1));
    }

    @Test
    public void testPasserAuQuizSuivant() {
        int premierQuiz = emploiDuTemps.getQuizActuel();
        emploiDuTemps.passerAuQuizSuivant();
        int deuxiemeQuiz = emploiDuTemps.getQuizActuel();
        assertNotEquals(premierQuiz, deuxiemeQuiz);
    }

    @Test
    public void testEstDernierQuiz() {
        assertFalse(emploiDuTemps.estDernierQuiz());
        emploiDuTemps.passerAuQuizSuivant();
        emploiDuTemps.passerAuQuizSuivant();
        assertTrue(emploiDuTemps.estDernierQuiz());
    }

    @Test
    public void testToString() {
        emploiDuTemps.validerEmploiDuTemps();
        String result = emploiDuTemps.toString();
        assertTrue(result.contains("Transformation numérique"));
        assertTrue(result.contains("Salle 1.01"));
    }

    @Test
    public void testToStringNonValide() {
        String result = emploiDuTemps.toString();
        assertTrue(result.contains("Vous n'avez pas encore d'emploi du temps valide."));
    }
}
