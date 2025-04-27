package test.jeu;

import jeu.Objet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjetTest {
    private Objet objetCle;
    private Objet objetNonCle;
    private Objet objetAvecDescription;

    @BeforeEach
    void setUp() {
        objetCle = new Objet("CLE", "Objet Clé", true, true);
        objetNonCle = new Objet("NONCLE", "Objet Non Clé", false, true);
        objetAvecDescription = new Objet("DESC", "Objet avec Description", true, true, "Description détaillée");
    }

    @Test
    void testConstructeur() {
        assertEquals("CLE", objetCle.getIdObjet());
        assertEquals("Objet Clé", objetCle.getLabel());
        assertTrue(objetCle.isKeyObject());
        assertTrue(objetCle.isUsable());
        assertEquals("", objetCle.getDescription());
    }

    @Test
    void testConstructeurAvecDescription() {
        assertEquals("DESC", objetAvecDescription.getIdObjet());
        assertEquals("Objet avec Description", objetAvecDescription.getLabel());
        assertTrue(objetAvecDescription.isKeyObject());
        assertTrue(objetAvecDescription.isUsable());
        assertEquals("Description détaillée", objetAvecDescription.getDescription());
    }

    @Test
    void testEstObjetCle() {
        assertTrue(objetCle.isKeyObject());
        assertFalse(objetNonCle.isKeyObject());
    }

    @Test
    void testEstUtilisable() {
        assertTrue(objetCle.isUsable());
        assertTrue(objetNonCle.isUsable());
    }

    @Test
    void testGetInfos() {
        String infos = objetCle.getInfos();
        assertTrue(infos.contains("CLE"));
        assertTrue(infos.contains("Objet Clé"));
        assertTrue(infos.contains("true"));
    }
} 