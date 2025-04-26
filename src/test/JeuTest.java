package test;
import jeu.Jeu;
import jeu.Zone;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JeuTest {
    Jeu testJeu = new Jeu();
    Zone zone = new Zone("ZoneTest","Couloir.jpg");

    @Test
    public void testQuandSauvegarde(){
        testJeu.setZoneCourante(zone);
        testJeu.sauvegarderJeu();
        Assert.assertNotNull(testJeu.getActualGameState().getMember("zoneCourante"));

    }
}
