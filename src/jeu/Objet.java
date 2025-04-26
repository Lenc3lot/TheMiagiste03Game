package jeu;

public class Objet {
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

    public static final Objet CERTIFICATION = new Objet("CERTIF", "Certification MIAGE", true, true, 
        "Votre certification MIAGE qui sert aussi de TODO list");
    public static final Objet MAISON = new Objet("MAISON", "Maison de qualité", true, true,
        "Une représentation de la maison de la qualité en management");
    public static final Objet GUIDE_INTEGRATION = new Objet("GUIDE", "Guide d'intégration", true, true,
        "Guide pour l'intégration en transformation numérique");
    public static final Objet ARTICLE_BANGLADESH = new Objet("ARTICLE", "Article sur le Bangladesh", true, true,
        "Article géopolitique sur le Bangladesh");
    public static final Objet ON2 = new Objet("ON2", "O(n²)", true, true,
        "Représentation de la complexité algorithmique");
    public static final Objet SCRUM_BOOK = new Objet("SCRUM", "Scrum Book", true, true,
        "Livre sur la méthodologie Scrum");
    public static final Objet CLE_USB = new Objet("USB", "Clé USB Wooclap", true, true,
        "Clé USB contenant les présentations Wooclap");
    public static final Objet CHAT_GPT = new Objet("GPT", "Chat GPT", true, true,
        "Assistant d'intelligence artificielle");
    public static final Objet KEBAB = new Objet("KEBAB", "Kebab du ZAM ZAM", false, true,
        "Un délicieux kebab du ZAM ZAM");
    public static final Objet MASCOTTE = new Objet("MASCOTTE", "Mascotte du BDE", true, true,
        "La mascotte officielle du BDE");
    public static final Objet CAFE = new Objet("CAFE", "Café", false, true,
        "Un bon café pour se réveiller");
}
