package jeu;

import java.io.Serializable;

public class Objet implements Serializable {
    private String idObjet;
    private String labelObjet;
    private boolean isKeyObject;
    private boolean isUsable;
    private String description;

    public Objet(String unIdObjet, String labelObjet, boolean isKeyObject, boolean isUsable) {
        this(unIdObjet, labelObjet, isKeyObject, isUsable, "");
    }

    public Objet(String unIdObjet, String labelObjet, boolean isKeyObject, boolean isUsable, String description) {
        this.idObjet = unIdObjet;
        this.labelObjet = labelObjet;
        this.isKeyObject = isKeyObject;
        this.isUsable = isUsable;
        this.description = description;
    }

    /**
     *
     * @return : Renvoie sous forme de String les informations d'un objet
     */
    public String getInfos(){
        return "IdObjet : "+ idObjet+ ", labelObjet  : "+labelObjet+", isKeyObject : "+ isKeyObject+", isUsable : "+isUsable;
    }

	public String getLabel() {
	    return labelObjet;
	}

    public String getDescription() {
        return description;
    }

    public boolean isKeyObject() {
        return isKeyObject;
    }

    public boolean isUsable() {
        return isUsable;
    }


}
