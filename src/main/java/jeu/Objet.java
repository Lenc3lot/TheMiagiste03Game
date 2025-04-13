package jeu;

public class Objet {
    private String idObjet;
    private String labelObjet;
    private boolean isKeyObject;
    private boolean isUsable;

    public Objet(String unIdObjet, String labelObjet,boolean isKeyObject, boolean isUsable){
        this.idObjet = unIdObjet;
        this.labelObjet = labelObjet;
        this.isKeyObject = isKeyObject;
        this.isUsable = isUsable;
    }

    /**
     *
     * @return : Renvoie sous forme de String les informations d'un objet
     */
    public String getInfos(){
        return "IdObjet : "+ idObjet+ ", labelObjet  : "+labelObjet+", isKeyObject : "+ isKeyObject+", isUsable : "+isUsable;
    }

}
