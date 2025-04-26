package jeu.PNJ;

import jeu.Objet;

import java.io.Serializable;

abstract public class PNJ implements Serializable {
     String idPNJ;
     String nomPNJ;
     String[] texteInterraction;
     Objet objetADonner;

     public PNJ(String unIdPNJ,String unNomPNJ,String[] tabInterractions){
         this.idPNJ = unIdPNJ;
         this.nomPNJ = unNomPNJ;
         this.texteInterraction = tabInterractions;
     }

     public PNJ(String unIdPNJ,String unNomPNJ,String[] tabInterractions,Objet objetADonner){
         this.idPNJ = unIdPNJ;
         this.nomPNJ = unNomPNJ;
         this.texteInterraction = tabInterractions;
         this.objetADonner = objetADonner;
     }

     public String donnerObjet(){
        // TODO : Donne un objet et affiche un message
         return "Voici " + objetADonner.getLabel();
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
