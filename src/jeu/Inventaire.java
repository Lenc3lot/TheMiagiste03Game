package jeu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Inventaire implements Serializable {
    private List<Objet> stockObjet;
    
    public Inventaire(){
        this.stockObjet = new ArrayList<>();
    }
    /**
     * @return la liste des objets dans l'inventaire
     */
    public List<Objet> getObjets() {
        return stockObjet;
    }
    
    /**
     * Ajoute un objet à l'inventaire
     * @param objet L'objet à ajouter
     */
    public void ajouterObjet(Objet objet) {
        stockObjet.add(objet);
    }
    
    /**
     * Retire un objet de l'inventaire
     * @param objet L'objet à retirer
     * @return true si l'objet a été retiré, false sinon
     */
    public boolean retirerObjet(Objet objet) {
        return stockObjet.remove(objet);
    }
}
