package jeu;

import java.io.Serializable;

/**
 * Classe représentant un objet que le joueur peut trouver, utiliser ou collecter dans le jeu.
 *
 * Chaque objet possède un identifiant, un label, une description,
 * et des propriétés indiquant s'il est essentiel pour la progression du joueur ou utilisable.
 *
 * Cette classe est sérialisable pour permettre la sauvegarde de l'état du jeu.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class Objet implements Serializable {

    /** Identifiant unique de l'objet. */
    private String idObjet;

    /** Label affiché pour l'objet. */
    private String labelObjet;

    /** Indique si l'objet est un objet clé (essentiel pour gagner). */
    private boolean isKeyObject;

    /** Indique si l'objet est utilisable par le joueur. */
    private boolean isUsable;

    /** Description textuelle de l'objet. */
    private String description;

    /**
     * Constructeur d'objet sans description personnalisée.
     *
     * @param unIdObjet Identifiant de l'objet.
     * @param labelObjet Label affiché.
     * @param isKeyObject Indique si l'objet est clé.
     * @param isUsable Indique si l'objet est utilisable.
     */
    public Objet(String unIdObjet, String labelObjet, boolean isKeyObject, boolean isUsable) {
        this(unIdObjet, labelObjet, isKeyObject, isUsable, "");
    }

    /**
     * Constructeur d'objet avec description personnalisée.
     *
     * @param unIdObjet Identifiant de l'objet.
     * @param labelObjet Label affiché.
     * @param isKeyObject Indique si l'objet est clé.
     * @param isUsable Indique si l'objet est utilisable.
     * @param description Description textuelle.
     */
    public Objet(String unIdObjet, String labelObjet, boolean isKeyObject, boolean isUsable, String description) {
        this.idObjet = unIdObjet;
        this.labelObjet = labelObjet;
        this.isKeyObject = isKeyObject;
        this.isUsable = isUsable;
        this.description = description;
    }

    /**
     * Retourne une chaîne contenant toutes les informations de l'objet.
     *
     * @return Informations détaillées sous forme de texte.
     */
    public String getInfos() {
        return "IdObjet : " + idObjet + ", labelObjet : " + labelObjet + ", isKeyObject : " + isKeyObject + ", isUsable : " + isUsable;
    }

    /**
     * Retourne le label de l'objet.
     *
     * @return Label de l'objet.
     */
    public String getLabel() {
        return labelObjet;
    }

    /**
     * Retourne la description de l'objet.
     *
     * @return Description de l'objet.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Indique si l'objet est un objet clé.
     *
     * @return true si c'est un objet clé, false sinon.
     */
    public boolean isKeyObject() {
        return isKeyObject;
    }

    /**
     * Indique si l'objet est utilisable.
     *
     * @return true si l'objet est utilisable, false sinon.
     */
    public boolean isUsable() {
        return isUsable;
    }

    public String getIdObjet(){
        return this.idObjet;
    }

}
