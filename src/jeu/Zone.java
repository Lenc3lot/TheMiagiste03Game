package jeu;

import jeu.PNJ.PNJ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe représentant une zone dans le jeu.
 *
 * Chaque zone possède une description, une image associée, des sorties vers d'autres zones,
 * des objets à récupérer, ainsi que des PNJ (personnages non joueurs) présents.
 *
 * Cette classe est sérialisable pour permettre la sauvegarde de l'état du jeu.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class Zone implements Serializable {

    /** Liste statique de toutes les zones du jeu. */
    private static final List<Zone> toutesLesZones = new ArrayList<>();

    /** Description textuelle de la zone. */
    private String description;

    /** Nom du fichier image représentant la zone. */
    private String nomImage;

    /** Nom de l'image alternative après la collecte d'un objet clé. */
    private String nomImageSansObjet;

    /** Sorties disponibles depuis cette zone (direction → zone voisine). */
    private HashMap<String,Zone> sorties;

    /** Liste des objets présents dans la zone. */
    private List<Objet> objets = new ArrayList<>();

    /** Liste des PNJ présents dans la zone. */
    private List<PNJ> pnjs = new ArrayList<>();

    /**
     * Constructeur de zone sans image alternative.
     *
     * @param description Description de la zone.
     * @param image Nom du fichier image associé.
     */
    public Zone(String description, String image) {
        this(description, image, null);
        toutesLesZones.add(this);
    }

    /**
     * Constructeur de zone avec image alternative.
     *
     * @param description Description de la zone.
     * @param image Nom du fichier image initial.
     * @param imageSansObjet Nom de l'image après collecte d'un objet clé.
     */
    public Zone(String description, String image, String imageSansObjet) {
        this.description = description;
        this.nomImage = image;
        this.nomImageSansObjet = imageSansObjet;
        sorties = new HashMap<>();
        toutesLesZones.add(this);
    }

    /**
     * Ajoute une sortie à partir de cette zone vers une autre.
     *
     * @param sortie Direction de la sortie.
     * @param zoneVoisine Zone voisine accessible via cette sortie.
     */
    public void ajouteSortie(Sortie sortie, Zone zoneVoisine) {
        sorties.put(sortie.name(), zoneVoisine);
    }

    /**
     * Ajoute un objet dans la zone.
     *
     * @param objet Objet à ajouter.
     */
    public void ajouterObjet(Objet objet) {
        objets.add(objet);
    }

    /**
     * Ajoute un PNJ dans la zone.
     *
     * @param pnj Personnage non joueur à ajouter.
     */
    public void setPNJ(PNJ pnj) {
        pnjs.add(pnj);
    }

    /**
     * Retourne la liste des PNJ présents dans la zone.
     *
     * @return Liste des PNJ.
     */
    public List<PNJ> getPNJs() {
        return pnjs;
    }

    /**
     * Retourne le nom de l'image actuelle de la zone.
     *
     * @return Nom du fichier image.
     */
    public String nomImage() {
        return nomImage;
    }

    /**
     * Change l'image associée à la zone.
     *
     * @param nouvelleImage Nouveau nom du fichier image.
     */
    public void changerImage(String nouvelleImage) {
        this.nomImage = nouvelleImage;
    }

    /**
     * Retourne la description de la zone.
     *
     * @return Description textuelle.
     */
    public String toString() {
        return description;
    }

    /**
     * Retourne une description détaillée de la zone,
     * incluant les sorties et les personnages présents.
     *
     * @return Description longue de la zone.
     */
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

    /**
     * Retourne la liste des sorties disponibles sous forme de chaîne.
     *
     * @return Liste des directions disponibles.
     */
    private String sorties() {
        return sorties.keySet().toString();
    }

    /**
     * Récupère la zone voisine accessible dans une certaine direction.
     *
     * @param direction Direction demandée (NORD, SUD, etc.).
     * @return Zone voisine ou null si inexistante.
     */
    public Zone obtientSortie(String direction) {
    	return sorties.get(direction);
    }

    /**
     * Tente de retirer un objet spécifique de la zone selon son nom.
     *
     * @param nomObjet Nom de l'objet à retirer.
     * @return L'objet retiré, ou null si non trouvé.
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
     * Récupère la liste des objets présents dans la zone.
     *
     * @return Liste des objets de la zone.
     */
    public List<Objet> getObjets() {
        return objets;
    }

    /**
     * Retourne la liste de toutes les zones du jeu.
     *
     * @return Liste de toutes les zones.
     */
    public static List<Zone> getAllZones() {
        return new ArrayList<>(toutesLesZones);
    }

    /**
     * Retourne une zone par son nom.
     *
     * @param nom Nom de la zone recherchée.
     * @return La zone correspondante ou null si non trouvée.
     */
    public static Zone getZoneParNom(String nom) {
        for (Zone zone : toutesLesZones) {
            if (zone.getNom().equalsIgnoreCase(nom)) {
                return zone;
            }
        }
        return null;
    }

    /**
     * Retourne le nom de la zone.
     *
     * @return Le nom de la zone.
     */
    public String getNom() {
        return description;
    }
}

