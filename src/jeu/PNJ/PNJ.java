package jeu.PNJ;

import jeu.Objet;

import java.io.Serializable;

abstract public class PNJ implements Serializable {
     String idPNJ;
     String nomPNJ;
     String[] texteInterraction;

     public PNJ(String unIdPNJ,String unNomPNJ,String[] tabInterractions){
         this.idPNJ = unIdPNJ;
         this.nomPNJ = unNomPNJ;
         this.texteInterraction = tabInterractions;
     }

     public String donnerObjet(Objet objet){
        // TODO : Donne un objet et affiche un message
         return "Voici " + objet.getLabel();
     }

     public String getNomPNJ() {
         return nomPNJ;
     }

     public String getIdPNJ() {
         return idPNJ;
     }

     public String[] getTexteInterraction() {
         return texteInterraction;
     }
}
