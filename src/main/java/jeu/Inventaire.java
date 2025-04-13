package jeu;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Inventaire {
    private List<Objet> stockObjet;
    
    public Inventaire(){
        this.stockObjet = new ArrayList<>();
    }

    public JSONObject saveInventory(){
        return new JSONObject(this);
    }
}
