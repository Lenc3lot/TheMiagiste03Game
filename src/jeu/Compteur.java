package jeu;

public  class Compteur implements Runnable {
//    private int secondes = 1080;
    private int secondes = 470;
    private GUI gui;
    private int startingTime = 465;
    private Jeu currentJeu;

    public Compteur(GUI actualGUI) {
        this.gui = actualGUI;
    }

    @Override
    public void run() {
        try {
            while (startingTime <= secondes) {
                gui.afficherCompteur("Heure acutelle : " + ((startingTime % 86400) % 3600) / 60 +" H " + ((startingTime % 86400) % 3600) % 60 + " ");
                Thread.sleep(1000); // attendre 1 seconde
                startingTime++;
            }
            timeIsOut();
            System.out.println("Compte à rebours terminé !");
        } catch (InterruptedException e) {
            System.out.println("Compte à rebours interrompu !");
        }
    }

    public int getTimeLeft(){
        return secondes;
    }

    public void timeIsOut(){
        currentJeu.timeIsOut();
    }

    public void setCurrentJeu(Jeu currentJeu){
        this.currentJeu = currentJeu;
    }

    public void setTimeBack(int timeLeftLoaded){
        this.secondes = timeLeftLoaded;
    }
}

