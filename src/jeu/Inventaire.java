package jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant l'inventaire du joueur.
 *
 * L'inventaire contient la liste des objets que le joueur a collectés au cours de son aventure.
 * Il permet d'ajouter ou de retirer des objets selon les actions du joueur.
 *
 * Cette classe est sérialisable pour permettre la sauvegarde de l'état du jeu.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class Inventaire implements Serializable {

    /** Liste des objets actuellement stockés dans l'inventaire. */
    private List<Objet> stockObjet;

    /**
     * Constructeur de l'inventaire.
     * Initialise une liste vide d'objets.
     */
    public Inventaire(){
        this.stockObjet = new ArrayList<>();
    }

    /**
     * Retourne la liste des objets présents dans l'inventaire.
     *
     * @return Liste des objets de l'inventaire.
     */
    public List<Objet> getObjets() {
        return stockObjet;
    }

    /**
     * Ajoute un objet à l'inventaire.
     *
     * @param objet L'objet à ajouter.
     */
    public void ajouterObjet(Objet objet) {
        stockObjet.add(objet);
    }

    /**
     * Retire un objet de l'inventaire.
     *
     * @param objet L'objet à retirer.
     * @return true si l'objet a été retiré avec succès, false sinon.
     */
    public boolean retirerObjet(Objet objet) {
        return stockObjet.remove(objet);
    }
}
