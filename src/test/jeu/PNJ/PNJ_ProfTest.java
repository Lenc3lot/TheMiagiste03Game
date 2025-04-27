package test.jeu.PNJ;

import jeu.Objet;
import jeu.Question;
import jeu.PNJ.PNJ_Prof;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PNJ_ProfTest {
    private PNJ_Prof prof;
    private Objet objet;
    private Question question;

    @BeforeEach
    public void setUp() {
        objet = new Objet("TEST", "Test Object", false, false);
        prof = new PNJ_Prof("TEST_PROF", "Test Prof", new String[]{"Test dialogue"}, objet, "Test Matiere", 1);
        question = new Question(1, "Test question", "Test answer");
        prof.ajouterQuestion(question);
    }

    @Test
    public void testConstructeur() {
        assertEquals("Test Prof", prof.getNomPNJ());
        assertEquals("TEST_PROF", prof.getIdPNJ());
        assertArrayEquals(new String[]{"Test dialogue"}, prof.getTexteInterraction());
        assertEquals(1, prof.getIdQuiz());
    }

    @Test
    public void testDonnerQuiz() {
        String quiz = prof.donnerQuiz();
        assertTrue(quiz.contains("Test Matiere"));
        assertTrue(quiz.contains("Test question"));
        assertTrue(prof.hasGivenQuiz());
    }

    @Test
    public void testDonnerQuizDejaDonne() {
        prof.donnerQuiz();
        String quiz = prof.donnerQuiz();
        assertTrue(quiz.contains("Vous avez déjà passé le quiz"));
    }

    @Test
    public void testIsAnswerTrue() {
        assertTrue(prof.isAnswerTrue("Test answer"));
        assertFalse(prof.isAnswerTrue("Wrong answer"));
    }

    @Test
    public void testGetQuestions() {
        assertEquals(1, prof.getQuestions().size());
        assertEquals(question, prof.getQuestions().get(0));
    }
} 