package test.jeu;

import jeu.Inventaire;
import jeu.Objet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class InventaireTest {
    private Inventaire inventaire;
    private Objet objet1;
    private Objet objet2;

    @BeforeEach
    void setUp() {
        inventaire = new Inventaire();
        objet1 = new Objet("OBJ1", "Objet 1", true, true);
        objet2 = new Objet("OBJ2", "Objet 2", false, true);
    }

    @Test
    void testAjouterObjet() {
        inventaire.ajouterObjet(objet1);
        List<Objet> objets = inventaire.getObjets();
        assertEquals(1, objets.size());
        assertEquals(objet1, objets.get(0));
    }

    @Test
    void testRetirerObjet() {
        inventaire.ajouterObjet(objet1);
        inventaire.ajouterObjet(objet2);
        
        boolean retiré = inventaire.retirerObjet(objet1);
        assertTrue(retiré);
        
        List<Objet> objets = inventaire.getObjets();
        assertEquals(1, objets.size());
        assertEquals(objet2, objets.get(0));
    }

    @Test
    void testRetirerObjetInexistant() {
        inventaire.ajouterObjet(objet1);
        
        boolean retiré = inventaire.retirerObjet(objet2);
        assertFalse(retiré);
        
        List<Objet> objets = inventaire.getObjets();
        assertEquals(1, objets.size());
    }

    @Test
    void testGetObjets() {
        inventaire.ajouterObjet(objet1);
        inventaire.ajouterObjet(objet2);
        
        List<Objet> objets = inventaire.getObjets();
        assertEquals(2, objets.size());
        assertTrue(objets.contains(objet1));
        assertTrue(objets.contains(objet2));
    }
} 