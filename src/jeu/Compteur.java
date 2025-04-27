package jeu;

/**
 * Classe représentant un compteur de temps pour le jeu.
 * Le compteur affiche le temps écoulé à l'aide d'une interface graphique (GUI)
 * et déclenche une action spécifique lorsque le temps est écoulé.
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */

public  class Compteur implements Runnable {

    /** Temps maximum en secondes (18 minutes). */
    private int secondes = 1080;

    /** Référence vers l'interface graphique du jeu. */
    private GUI gui;

    /** Temps de départ en secondes. */
    private int startingTime = 465;

    /** Référence vers la partie actuelle du jeu. */
    private Jeu currentJeu;

    /**
     * Constructeur du compteur.
     *
     * @param actualGUI Interface graphique à utiliser pour afficher le temps.
     */
    public Compteur(GUI actualGUI) {
        this.gui = actualGUI;
    }

    /**
     * Méthode principale exécutée par le thread du compteur.
     * Affiche le temps écoulé et déclenche une action lorsque le temps est écoulé.
     */
    @Override
    public void run() {
        try {
            while (startingTime <= secondes) {
                gui.afficherCompteur("HORLOGE : " + ((startingTime % 86400) % 3600) / 60 +" H " + ((startingTime % 86400) % 3600) % 60 + " ");
                Thread.sleep(2000); // attendre 2 secondes
                startingTime++;
            }
            timeIsOut();
            System.out.println("Compte à rebours terminé !");
        } catch (InterruptedException e) {
            System.out.println("Compte à rebours interrompu !");
        }
    }

    /**
     * Retourne le temps actuel écoulé depuis le début du compteur.
     *
     * @return Temps écoulé en secondes.
     */
    public int getTimeLeft(){
        return startingTime;
    }

    /**
     * Méthode appelée lorsque le temps est écoulé.
     * Elle signale à la partie en cours que le temps est terminé.
     */
    public void timeIsOut(){
        currentJeu.timeIsOut();
    }

    /**
     * Définit la partie du jeu associée au compteur.
     *
     * @param currentJeu Partie actuelle du jeu.
     */
    public void setCurrentJeu(Jeu currentJeu){
        this.currentJeu = currentJeu;
    }

    /**
     * Recharge le temps de départ depuis une sauvegarde.
     *
     * @param timeLeftLoaded Temps à recharger en secondes.
     */
    public void setTimeBack(int timeLeftLoaded){
        this.startingTime = timeLeftLoaded;
    }
}

