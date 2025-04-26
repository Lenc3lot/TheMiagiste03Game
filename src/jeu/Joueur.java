package jeu;

public class Joueur {
    private String pseudo;
    private Inventaire inventaireJoueur;
    private boolean isLogged;

    public Joueur(String unPseudo) {
        this.pseudo = unPseudo;
        this.inventaireJoueur = new Inventaire();
        this.isLogged = false;
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

    public void setPseudo(String pseudo) {
        if (!isLogged){
            this.pseudo = pseudo;
            this.isLogged = true;
        }
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public Inventaire getInventaireJoueur() {
        return inventaireJoueur;
    }
}


