package jeu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class GUI implements ActionListener
{
    private Jeu jeu;
    private JFrame fenetre;
    private JTextField entree;
    private JTextArea texte;
    private JTextArea zoneCompteur;
    private JLabel image;

    public GUI(Jeu j) {
        jeu = j;
        creerGUI();
    }

    public void afficherCompteur (String cpt){
        zoneCompteur.setText(cpt);
        zoneCompteur.setCaretPosition(zoneCompteur.getDocument().getLength());
    }

    public void afficher(String s) {
        texte.append(s);
        texte.setCaretPosition(texte.getDocument().getLength());
    }
    
    public void afficher() {
        afficher("\n");
    }

   public void afficheImage( String nomImage) {
	   	URL imageURL = this.getClass().getClassLoader().getResource("jeu/images/" + nomImage);
	   	if( imageURL != null ) {
        	image.setIcon( new ImageIcon( imageURL ));
            fenetre.pack();
        }
   }

    public void enable(boolean ok) {
        entree.setEditable(ok);
        if ( ! ok )
            entree.getCaret().setBlinkRate(0);
    }

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

        listScroller.setPreferredSize(new Dimension(600, 400));
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
        fenetre.setMinimumSize(new Dimension(800, 600));
        fenetre.setLocationRelativeTo(null);

        entree.addActionListener(this);

        fenetre.pack();
        fenetre.setVisible(true);
        entree.requestFocus();
    }

    public void actionPerformed(ActionEvent e) {
        executerCommande();
    }

    private void executerCommande() {
        String commandeLue = entree.getText();
        entree.setText("");
        jeu.traiterCommande( commandeLue);
    }
}