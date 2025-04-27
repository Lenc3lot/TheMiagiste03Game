package test.jeu.PNJ;

import jeu.Objet;
import jeu.PNJ.PNJ_ZamZam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PNJ_ZamZamTest {
    private PNJ_ZamZam zamzam;

    @BeforeEach
    public void setUp() {
        zamzam = new PNJ_ZamZam("ZAM", "Chef du ZAM ZAM", new String[]{"Bonjour", "Comment puis-je vous aider ?"});
    }

    @Test
    public void testConstructeur() {
        assertEquals("Chef du ZAM ZAM", zamzam.getNomPNJ());
        assertEquals("ZAM", zamzam.getIdPNJ());
        assertArrayEquals(new String[]{"Bonjour", "Comment puis-je vous aider ?"}, zamzam.getTexteInterraction());
    }

    @Test
    public void testDonnerSandwichPremierAppel() {
        Objet sandwich = zamzam.donnerSandwich();
        assertNotNull(sandwich);
        assertEquals("SANDWICH", sandwich.getIdObjet());
        assertEquals("Magnifique sandwich du ZAM ZAM", sandwich.getLabel());
    }

    @Test
    public void testDonnerSandwichAppelsSuivants() {
        zamzam.donnerSandwich();
        Objet sandwich = zamzam.donnerSandwich();
        assertNull(sandwich);
    }

    @Test
    public void testGetSandwich() {
        Objet sandwich = zamzam.getSandwich();
        assertNotNull(sandwich);
        assertEquals("SANDWICH", sandwich.getIdObjet());
        assertEquals("Magnifique sandwich du ZAM ZAM", sandwich.getLabel());
    }
} 