package jeu;

public class Joueur {
    private String pseudo;

    public Joueur(String unPseudo) {
        this.pseudo = unPseudo;
    }

    public String parler(String pnj) {
        // TODO
        return "";
    }

    public void prendre(String objet) {
        // Récupère un objet
    }

    public String inspecter(String element) {
        // TODO : Doit retourner une string qui dit si la zone contient ou non un element a ouvrir ou prendre
        return "";
    }

    public String ouvrir(String conteneur) {
        //TODO : Doit retourner une string qui dit que si quelque-chose est dans l'élément ouvert
        return "";
    }

    public String utiliser(String objet) {
        //TODO : Retourne une string liée à l'objet
        return "";
    }


}


