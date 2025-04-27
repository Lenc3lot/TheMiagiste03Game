package jeu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Classe représentant l'interface graphique textuelle du jeu.
 * Elle permet d'afficher les textes du jeu, les images associées aux zones,
 * et de saisir les commandes utilisateur.
 *
 * Elle gère aussi l'affichage d'un compteur de temps.
 *
 * @author Amine Amar
 * @author Amine Foufa
 * @author Baptiste Noto
 * @version 1.0
 */

public class GUI implements ActionListener
{
    /** Référence vers l'instance du jeu. */
    private Jeu jeu;

    /** Fenêtre principale de l'application. */
    private JFrame fenetre;

    /** Champ de saisie pour entrer les commandes. */
    private JTextField entree;

    /** Zone de texte principale affichant les messages du jeu. */
    private JTextArea texte;

    /** Zone de texte dédiée à l'affichage du compteur. */
    private JTextArea zoneCompteur;

    /** Label pour afficher les images liées aux zones. */
    private JLabel image;

    /**
     * Constructeur de l'interface graphique.
     *
     * @param j Instance du jeu associée à cette interface.
     */
    public GUI(Jeu j) {
        jeu = j;
        creerGUI();
    }

    /**
     * Affiche la valeur actuelle du compteur.
     *
     * @param cpt Chaîne de caractères représentant le temps actuel.
     */
    public void afficherCompteur (String cpt){
        zoneCompteur.setText(cpt);
        zoneCompteur.setCaretPosition(zoneCompteur.getDocument().getLength());
    }

    /**
     * Affiche un texte dans la zone principale.
     *
     * @param s Texte à afficher.
     */
    public void afficher(String s) {
        texte.append(s);
        texte.setCaretPosition(texte.getDocument().getLength());
    }

    /**
     * Ajoute une ligne vide dans la zone principale.
     */
    public void afficher() {
        afficher("\n");
    }

    /**
     * Affiche une image en fonction du nom fourni.
     *
     * @param nomImage Nom du fichier image à afficher.
     */
   public void afficheImage( String nomImage) {
	   	URL imageURL = this.getClass().getClassLoader().getResource("jeu/images/" + nomImage);
	   	if( imageURL != null ) {
        	image.setIcon( new ImageIcon( imageURL ));
            fenetre.pack();
        }
   }

    /**
     * Active ou désactive la saisie utilisateur.
     *
     * @param ok true pour activer la saisie, false pour la désactiver.
     */
    public void enable(boolean ok) {
        entree.setEditable(ok);
        if ( ! ok )
            entree.getCaret().setBlinkRate(0);
    }

    /**
     * Crée et configure tous les éléments de l'interface graphique.
     */
    private void creerGUI() {
        fenetre = new JFrame("Jeu");
        
        // Configuration du champ de saisie
        entree = new JTextField(34);
        entree.setFont(new Font("Consolas", Font.PLAIN, 14));
        entree.setBackground(new Color(240, 240, 240));
        entree.setForeground(new Color(50, 50, 50));
        entree.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // Configuration de la zone de texte principale
        texte = new JTextArea();
        texte.setEditable(false);
        texte.setFont(new Font("Consolas", Font.PLAIN, 14));
        texte.setBackground(new Color(250, 250, 250));
        texte.setForeground(new Color(40, 40, 40));
        texte.setLineWrap(true);
        texte.setWrapStyleWord(true);
        texte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Configuration de la zone du compteur
        zoneCompteur = new JTextArea();
        zoneCompteur.setEditable(false);
        zoneCompteur.setFont(new Font("Consolas", Font.BOLD, 14));
        zoneCompteur.setBackground(new Color(230, 230, 230));
        zoneCompteur.setForeground(new Color(60, 60, 60));
        zoneCompteur.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JScrollPane listScroller = new JScrollPane(texte);
        JScrollPane textScroller = new JScrollPane(zoneCompteur);

        listScroller.setPreferredSize(new Dimension(600, 200));
        listScroller.setMinimumSize(new Dimension(300, 200));
        listScroller.setBorder(BorderFactory.createEmptyBorder());

        textScroller.setPreferredSize(new Dimension(200, 50));
        textScroller.setMinimumSize(new Dimension(200, 50));
        textScroller.setBorder(BorderFactory.createEmptyBorder());

        JPanel panel = new JPanel();
        image = new JLabel();

        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(250, 250, 250));
        panel.add(image, BorderLayout.NORTH);
        panel.add(listScroller, BorderLayout.CENTER);
        panel.add(textScroller, BorderLayout.EAST);
        panel.add(entree, BorderLayout.SOUTH);

        fenetre.getContentPane().add(panel, BorderLayout.CENTER);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setMinimumSize(new Dimension(-00, 600));
        fenetre.setLocationRelativeTo(null);

        entree.addActionListener(this);

        fenetre.pack();
        fenetre.setVisible(true);
        entree.requestFocus();
    }

    /**
     * Action déclenchée lorsqu'une commande est entrée par l'utilisateur.
     *
     * @param e Événement d'action lié à la saisie de texte.
     */
    public void actionPerformed(ActionEvent e) {
        executerCommande();
    }

    /**
     * Lit la commande entrée par l'utilisateur et la transmet au jeu pour traitement.
     */
    private void executerCommande() {
        String commandeLue = entree.getText();
        entree.setText("");
        jeu.traiterCommande( commandeLue);
    }
}