package jeu.PNJ;

import jeu.Objet;

abstract public class PNJ {
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
         return "";
     }
}
