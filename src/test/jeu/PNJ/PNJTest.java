package test.jeu.PNJ;

import jeu.Objet;
import jeu.PNJ.PNJ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PNJTest {
    private PNJ pnj;
    private Objet objet;

    @BeforeEach
    public void setUp() {
        pnj = new PNJ("TEST_PNJ", "Test PNJ", new String[]{"Test dialogue"}) {};
        objet = new Objet("TEST", "Test Object", false, false, "Test description");
    }

    @Test
    public void testConstructeur() {
        assertEquals("Test PNJ", pnj.getNomPNJ());
        assertEquals("TEST_PNJ", pnj.getIdPNJ());
        assertArrayEquals(new String[]{"Test dialogue"}, pnj.getTexteInterraction());
    }

    @Test
    public void testGetTexteInterraction() {
        String[] dialogues = pnj.getTexteInterraction();
        assertEquals(1, dialogues.length);
        assertEquals("Test dialogue", dialogues[0]);
    }
} 