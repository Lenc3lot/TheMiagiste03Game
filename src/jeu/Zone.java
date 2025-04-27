package jeu;
import java.io.Serializable;
import jeu.PNJ.PNJ;


import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Zone implements Serializable {
    private String description;
    private String nomImage;
    private String nomImageSansObjet;  // Image à afficher quand l'objet est pris
    private HashMap<String,Zone> sorties;
    private List<Objet> objets = new ArrayList<>();
    private List<PNJ> pnjs = new ArrayList<>();

    public Zone(String description, String image) {
        this(description, image, null);
    }

    public Zone(String description, String image, String imageSansObjet) {
        this.description = description;
        this.nomImage = image;
        this.nomImageSansObjet = imageSansObjet;
        sorties = new HashMap<>();
    }

    public void ajouteSortie(Sortie sortie, Zone zoneVoisine) {
        sorties.put(sortie.name(), zoneVoisine);
    }

    public void ajouterObjet(Objet objet) {
        objets.add(objet);
    }

    public void setPNJ(PNJ pnj) {
        pnjs.add(pnj);
    }

    public List<PNJ> getPNJs() {
        return pnjs;
    }

    public String nomImage() {
        return nomImage;
    }

    public void changerImage(String nouvelleImage) {
        this.nomImage = nouvelleImage;
    }

    public String toString() {
        return description;
    }

    public String descriptionLongue()  {
        StringBuilder sb = new StringBuilder();
        sb.append(UIHelper.line());
        sb.append(UIHelper.center("ZONE ACTUELLE"));
        sb.append(UIHelper.line());
        sb.append(UIHelper.left("Vous êtes dans : " + description));
        sb.append(UIHelper.left("Sorties disponibles : " + sorties()));
        sb.append(UIHelper.line());

        if (!pnjs.isEmpty()) {
            sb.append("\n");
            sb.append(UIHelper.line());
            sb.append(UIHelper.center("PERSONNES PRÉSENTES"));
            sb.append(UIHelper.line());
            int index = 1;
            for (PNJ pnj : pnjs) {
                sb.append(UIHelper.left(index + ". " + pnj.getNomPNJ()));
                index++;
            }
            sb.append(UIHelper.line());
            sb.append(UIHelper.left("Pour parler à quelqu'un, utilisez PARLER"));
            sb.append(UIHelper.left("suivi de son numéro ou de son nom."));
            sb.append(UIHelper.line());
        }
        return sb.toString();
    }

    private String sorties() {
        return sorties.keySet().toString();
    }

    public Zone obtientSortie(String direction) {
    	return sorties.get(direction);
    }

    /**
     * Tente de retirer un objet de la zone par son nom
     * @param nomObjet Le nom de l'objet à retirer
     * @return L'objet retiré ou null si l'objet n'existe pas
     */
    public Objet retirerObjet(String nomObjet) {
        for (int i = 0; i < objets.size(); i++) {
            Objet obj = objets.get(i);
            if (obj.getLabel().toUpperCase().equals(nomObjet)) {
                objets.remove(i);
                if (obj.isKeyObject() && nomImageSansObjet != null) {
                    changerImage(nomImageSansObjet);
                }
                return obj;
            }
        }
        return null;
    }

    /**
     * Récupère la liste des objets présents dans la zone
     * @return La liste des objets
     */
    public List<Objet> getObjets() {
        return objets;
    }
}

