package jeu;

public  class Compteur implements Runnable {
    private int secondes = 1080;
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
                Thread.sleep(2000); // attendre 2 secondes
                startingTime++;
            }
            timeIsOut();
            System.out.println("Compte à rebours terminé !");
        } catch (InterruptedException e) {
            System.out.println("Compte à rebours interrompu !");
        }
    }

    public int getTimeLeft(){
        return startingTime;
    }

    public void timeIsOut(){
        currentJeu.timeIsOut();
    }

    public void setCurrentJeu(Jeu currentJeu){
        this.currentJeu = currentJeu;
    }

    public void setTimeBack(int timeLeftLoaded){
        this.startingTime = timeLeftLoaded;
    }
}

