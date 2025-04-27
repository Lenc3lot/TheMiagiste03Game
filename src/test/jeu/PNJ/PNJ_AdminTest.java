package test.jeu.PNJ;

import jeu.Objet;
import jeu.PNJ.PNJ_Admin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PNJ_AdminTest {
    private PNJ_Admin sandrine;
    private Objet emploiDuTemps;

    @BeforeEach
    public void setUp() {
        sandrine = new PNJ_Admin("ADMIN", "Sandrine", new String[]{"Bonjour", "Comment puis-je vous aider ?"});
        emploiDuTemps = new Objet("EDT", "Emploi du temps MIAGE", true, true);
    }

    @Test
    public void testConstructeur() {
        assertEquals("Sandrine", sandrine.getNomPNJ());
        assertEquals("ADMIN", sandrine.getIdPNJ());
        assertArrayEquals(new String[]{"Bonjour", "Comment puis-je vous aider ?"}, sandrine.getTexteInterraction());
    }

    @Test
    public void testDonnerEmploiDuTemps() {
        String message = sandrine.donnerEmploiDuTemps();
        assertTrue(message.contains("Félicitations ! Voici votre emploi du temps."));
        assertTrue(message.contains("Vous devez suivre tous vos cours et passer les examens."));
        assertTrue(message.contains("N'oubliez pas de vous rendre dans les salles indiquées."));
        assertTrue(message.contains("Attention l'examen final doit être passé avant 18h en revenant me voir !"));
    }

    @Test
    public void testDonnerEmploiDuTempsDejaDonne() {
        sandrine.donnerEmploiDuTemps();
        String message = sandrine.donnerEmploiDuTemps();
        assertEquals("Vous avez déjà votre emploi du temps !", message);
    }

    @Test
    public void testGetListeQuizs() {
        assertNotNull(sandrine.getListeQuizs());
        assertEquals(6, sandrine.getListeQuizs().size());
        assertTrue(sandrine.getListeQuizs().contains(1));
        assertTrue(sandrine.getListeQuizs().contains(2));
        assertTrue(sandrine.getListeQuizs().contains(3));
        assertTrue(sandrine.getListeQuizs().contains(4));
        assertTrue(sandrine.getListeQuizs().contains(5));
        assertTrue(sandrine.getListeQuizs().contains(6));
    }
} 