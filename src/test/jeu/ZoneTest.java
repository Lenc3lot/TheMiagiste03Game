package test.jeu;

import jeu.Zone;
import jeu.Objet;
import jeu.PNJ.PNJ;
import jeu.Sortie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ZoneTest {
    private Zone zone;
    private Zone zoneVoisine;
    private Objet objet;
    private PNJ pnj;

    @BeforeEach
    public void setUp() {
        zone = new Zone("Test Zone", "test.png", "test_sans_objet.png");
        zoneVoisine = new Zone("Voisine", "voisine.png");
        objet = new Objet("TEST", "Test Object", false, false, "Test description");
        pnj = new PNJ("TEST_PNJ", "Test PNJ", new String[]{"Test dialogue"}) {};
    }

    @Test
    public void testConstructeur() {
        assertEquals("Test Zone", zone.toString());
        assertEquals("test.png", zone.nomImage());
        assertTrue(zone.getObjets().isEmpty());
        assertTrue(zone.getPNJs().isEmpty());
    }

    @Test
    public void testAjouteSortie() {
        zone.ajouteSortie(Sortie.NORD, zoneVoisine);
        assertEquals(zoneVoisine, zone.obtientSortie("NORD"));
        assertNull(zone.obtientSortie("SUD"));
    }

    @Test
    public void testAjouterObjet() {
        zone.ajouterObjet(objet);
        List<Objet> objets = zone.getObjets();
        assertEquals(1, objets.size());
        assertEquals(objet, objets.get(0));
    }

    @Test
    public void testRetirerObjet() {
        zone.ajouterObjet(objet);
        Objet retiré = zone.retirerObjet("TEST");
        assertEquals(objet, retiré);
        assertTrue(zone.getObjets().isEmpty());
        assertEquals("test_sans_objet.png", zone.nomImage());
    }

    @Test
    public void testRetirerObjetInexistant() {
        assertNull(zone.retirerObjet("INEXISTANT"));
    }

    @Test
    public void testSetPNJ() {
        zone.setPNJ(pnj);
        List<PNJ> pnjs = zone.getPNJs();
        assertEquals(1, pnjs.size());
        assertEquals(pnj, pnjs.get(0));
    }

    @Test
    public void testChangerImage() {
        zone.changerImage("nouvelle.png");
        assertEquals("nouvelle.png", zone.nomImage());
    }

    @Test
    public void testDescriptionLongue() {
        zone.ajouteSortie(Sortie.NORD, zoneVoisine);
        zone.setPNJ(pnj);
        String description = zone.descriptionLongue();
        assertTrue(description.contains("Test Zone"));
        assertTrue(description.contains("NORD"));
        assertTrue(description.contains("Test PNJ"));
    }
} 