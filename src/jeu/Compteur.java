package jeu;

public  class Compteur implements Runnable {
    private int secondes;
    private GUI gui;

    public Compteur(int seconds, GUI actualGUI) {
        this.secondes = seconds;
        this.gui = actualGUI;
    }

    @Override
    public void run() {
        try {
            while (secondes > 0) {
                gui.afficherCompteur("Temps restant : " + secondes + " secondes");
                Thread.sleep(1000); // attendre 1 seconde
                secondes--;
            }
            System.out.println("Compte à rebours terminé !");
        } catch (InterruptedException e) {
            System.out.println("Compte à rebours interrompu !");
        }
    }

    public int getTimeLeft(){
        return secondes;
    }
}

