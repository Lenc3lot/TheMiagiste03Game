package test.jeu.PNJ;

import jeu.PNJ.PNJ_Guide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PNJ_GuideTest {
    private PNJ_Guide guide;
    private String[] interactions;

    @BeforeEach
    public void setUp() {
        interactions = new String[]{"Bonjour", "Comment puis-je vous aider ?"};
        guide = new PNJ_Guide("SIN", "SIN", interactions);
    }

    @Test
    public void testConstructeur() {
        assertEquals("SIN", guide.getNomPNJ());
        assertEquals("SIN", guide.getIdPNJ());
        assertArrayEquals(interactions, guide.getTexteInterraction());
    }

    @Test
    public void testDonnerIndicePremierAppel() {
        String indice = guide.donnerIndice();
        assertTrue(indice.contains("Bienvenue à Forbin"));
        assertTrue(indice.contains("Votre objectif est de réussir votre année de MIAGE"));
        assertTrue(indice.contains("Commencez par aller voir Sandrine"));
        assertTrue(indice.contains("N'oubliez pas de passer au ZAM ZAM"));
    }

    @Test
    public void testDonnerIndiceAppelsSuivants() {
        // Premier appel
        guide.donnerIndice();
        
        // Appels suivants
        String indice = guide.donnerIndice();
        assertEquals("Vous êtes sur la bonne voie ! Continuez à explorer Forbin.", indice);
    }
} 