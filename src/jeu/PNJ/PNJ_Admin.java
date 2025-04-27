package jeu.PNJ;

import jeu.Compteur;
import jeu.Inventaire;
import jeu.Objet;
import jeu.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le PNJ administratif (Sandrine) dans le jeu.
 *
 * Ce personnage remet l'emploi du temps au joueur et est responsable du démarrage
 * de l'examen final après la collecte des objets clés nécessaires.
 *
 * Il contrôle également la liste des quiz que le joueur doit réussir.
 *
 * @see PNJ
 * @see Objet
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */
public class PNJ_Admin extends PNJ {

    /** Indique si l'emploi du temps a déjà été donné au joueur. */
    private boolean hasGivenSchedule;

    /** Objet représentant l'emploi du temps remis au joueur. */
    private final Objet emploiDuTemps;

    /** Indique si le joueur a réussi l'examen final (peut être null avant l'examen). */
    private Boolean isFinalExamSuccessed;

    /** Liste des identifiants des quiz à accomplir. */
    private final List<Integer> listeQuizs;
    private Question questionFinale;

    /**
     * Constructeur du PNJ administratif.
     *
     * @param unIdPNJ Identifiant du PNJ.
     * @param unNomPNJ Nom du PNJ.
     * @param tabInterractions Tableau des textes d'interaction disponibles.
     */
    public PNJ_Admin(String unIdPNJ, String unNomPNJ, String[] tabInterractions) {
        super(unIdPNJ, unNomPNJ, tabInterractions);
        this.hasGivenSchedule = false;
        this.emploiDuTemps = new Objet("EDT", "Emploi du temps MIAGE", true, true);
        this.listeQuizs = new ArrayList<>();
        listeQuizs.add(1);
        listeQuizs.add(2);
        listeQuizs.add(3);
        listeQuizs.add(4);
        listeQuizs.add(5);
        listeQuizs.add(6);
    }

    /**
     * Vérifie si tous les objets clés ont été collectés par le joueur.
     *
     * @return true si tous les objets ont été collectés, false sinon.
     */
    public boolean isAllKeyItemsCollected(Inventaire inventaireJoueur, Compteur compteur) {
        // TODO : Verifie si le nombre d'item clés ont étés collectés avant l'heure max
        if (compteur.getTimeLeft() < 1080) {
            List<Objet> objetList = inventaireJoueur.getObjets();
            int cptKeyObj = 0;
            for (Objet objet : objetList) {
                if (objet.isKeyObject()) {
                    cptKeyObj++;
                }
            }
            return cptKeyObj == 6;
        }
        return false;
    }

    /**
     * Donne l'emploi du temps au joueur s'il ne l'a pas encore reçu.
     *
     * @return Message de remise ou rappel si déjà donné.
     */
    public String donnerEmploiDuTemps() {
        if (!hasGivenSchedule) {
            hasGivenSchedule = true;
            return "Félicitations ! Voici votre emploi du temps.\n" +
                    "Vous devez suivre tous vos cours et passer les examens.\n" +
                    "N'oubliez pas de vous rendre dans les salles indiquées." +
                    "Attention l'examen final doit être passé avant 18h en revenant me voir !";
        }
        return "Vous avez déjà votre emploi du temps !";
    }

    /**
    * Retourne la liste des identifiants des quiz que le joueur doit suivre.
    *
    * @return Copie de la liste des identifiants de quiz.
    */
    public List<Integer> getListeQuizs() {
        return new ArrayList<>(listeQuizs); // Retourne une copie pour éviter les modifications externes
    }

    /**
    * Retourne la liste des identifiants des quiz que le joueur doit suivre.
    *
    * @return Copie de la liste des identifiants de quiz.
    */
    public String startExamFinal() {
        questionFinale = new Question(1,
                "Bravo tu as réussi à passer tout tes examens ! \n" +
                        "Mais avant d'être diplomé, il faut que tu répondes à une dernière question.\n" +
                        "La MIAGE c'est ...?\n" +
                        "A -Le Fromage\n" +
                        "B -Cool\n" +
                        "C -Le partage\n",
                "C");
        return questionFinale.getTextQuestion();
    }

    public Question getQuestionFinale(){
        return questionFinale;
    }
}
