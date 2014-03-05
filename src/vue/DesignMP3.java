/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controller.*;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import model.Bibliotheque;
import model.Observateur;

/**
 *
 * @author ilanmalka
 */
public class DesignMP3 extends Applet implements Observateur {

    private JPanel monPanel = new JPanel();
    private Operations operations;
    private JLabel affichageVolume;
    private JLabel auteur;
    private JLabel duree;
    private JLabel album;
    private JLabel titre;
    private Sound monSon;
    private Bibliotheque biblio;

    public DesignMP3() {

        this.biblio = new Bibliotheque("test");
        this.operations = new Operations();
        this.operations.ajouterObservateur(this);
        this.affichageVolume = new JLabel();
        this.auteur = new JLabel();
        this.duree = new JLabel();
        this.album = new JLabel();
        this.titre = new JLabel();
    }

    public JPanel initialisation() {
        monPanel.setLayout(new BorderLayout());
        monPanel.setBackground(Color.WHITE);
        this.panelSud();
        this.coteEst();
        this.lesOnglets();
        return this.monPanel;
    }

    public void panelSud() {

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Barre Musique
         *-------------------------------------------------------------------------------------------------------
         */

        JPanel barreMusique = new JPanel();
        barreMusique.setLayout((new BoxLayout(barreMusique, BoxLayout.LINE_AXIS)));
        barreMusique.add(new JLabel("Temps déroulant"));
        barreMusique.add(new JButton("Barre de Musique"));
        barreMusique.add(new JLabel("Temps total"));

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Boutons 
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel lesBoutons = new JPanel();
        lesBoutons.setLayout((new BoxLayout(lesBoutons, BoxLayout.LINE_AXIS)));

        JButton precedent = new JButton(new ImageIcon("Design/Boutons/precedent.png"));
        JButton lecture = new JButton(new ImageIcon("Design/Boutons/lecture.png"));
        JButton suivant = new JButton(new ImageIcon("Design/Boutons/suivant.png"));
        JButton diminuer = new JButton(new ImageIcon("Design/Boutons/baisserSon.png"));
        JButton augmenter = new JButton(new ImageIcon("Design/Boutons/augmenterSon.png"));


        lesBoutons.add(precedent);
        lesBoutons.add(lecture);
        lesBoutons.add(suivant);
        lesBoutons.add(diminuer);
        lesBoutons.add(this.affichageVolume);
        lesBoutons.add(augmenter);

        lecture.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {

                operations.lire();
                actualiserInformations();
            }
        });

        diminuer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                operations.diminuerVolume();
                actualiserInformations();
            }
        });

        augmenter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                actualiserInformations();
                operations.augmenterVolume();
            }
        });

        //lesBoutons.add(new JButton("barre Son"));

        /*
         *-------------------------------------------------------------------------------------------------------
         *                          Création du BoxLayout -- superposition des icones  
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel panneauBas = new JPanel();
        panneauBas.setLayout((new BoxLayout(panneauBas, BoxLayout.PAGE_AXIS)));
        panneauBas.add(barreMusique);
        panneauBas.add(lesBoutons);

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                  Création du BorderLayout  
         *-------------------------------------------------------------------------------------------------------
         */

        monPanel.add(panneauBas, BorderLayout.SOUTH);
    }

    public void lesOnglets() {

        final CardLayout cl = new CardLayout();
        JPanel lesOnglets = new JPanel();
        Font font = new Font("Courier", Font.BOLD, 26);
        lesOnglets.setLayout(new BoxLayout(lesOnglets, BoxLayout.LINE_AXIS));
        final JPanel content = new JPanel();
        final String[] listContent = {"CARD_1", "CARD_2", "CARD_3"};

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                     Ajout de musique
         *-------------------------------------------------------------------------------------------------------
         */

        JButton ajouterMusique = new JButton(new ImageIcon("Design/Boutons/ajoutMusique.png"));
        ajouterMusique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    operations.ouvrirFenetre();
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                biblio.ajouterMusique(operations.getSound());
                actualiserInformations();
            }
        });


        /*
         *-------------------------------------------------------------------------------------------------------
         *                                     Onglet Musique en cours
         *-------------------------------------------------------------------------------------------------------
         */

        final JPanel card1 = new JPanel();
        card1.setBackground(Color.RED);

        JButton musiqueEnCours = new JButton(new ImageIcon("Design/Boutons/lectureEnCours.png"));

        JLabel txtMusiqueEnCours = new JLabel();
        txtMusiqueEnCours.setText("<html><body><font color='white'>Musique en cours</body></html>");
        txtMusiqueEnCours.setToolTipText(txtMusiqueEnCours.getText());
        txtMusiqueEnCours.setFont(font);
        card1.add(txtMusiqueEnCours);
        musiqueEnCours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cl.show(content, listContent[0]);
            }
        });

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Onglet Bibliothèque
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel card2 = new JPanel();
        card2.setBackground(Color.GREEN);

        JButton bibliothèqe = new JButton(new ImageIcon("Design/Boutons/MaBibli.png"));

        JLabel txtBibliothèque = new JLabel();
        txtBibliothèque.setLayout(new BoxLayout(txtBibliothèque, BoxLayout.LINE_AXIS));
        txtBibliothèque.setText("<html><body><font color='white'>Bibliothèque</body></html>");
        txtBibliothèque.setToolTipText(txtBibliothèque.getText());
        txtBibliothèque.setFont(font);
        card2.add(txtBibliothèque);

        
        
        JPanel maBibli = new JPanel();
        maBibli.setLayout(new BoxLayout(maBibli, BoxLayout.LINE_AXIS));

        Font fontBold = new Font("Times New Roman", Font.PLAIN, 16);

        maBibli.setFont(fontBold);
        //titre.setText(operations.getTitre());
        maBibli.add(new JLabel("toto de merde"));
        
        ArrayList<Sound> test = new ArrayList<Sound>();
        test = biblio.label();
        
        for(int i=0;i<test.size();i++){
            
        
            maBibli.add(new JLabel("toto de merde"));
        
                
        }
        
        
        JPanel bibli = new JPanel();
        bibli.setLayout(new BoxLayout(bibli, BoxLayout.PAGE_AXIS));
        
        bibli.add(txtBibliothèque);
        bibli.add(maBibli);
        
        card2.add(bibli);

        bibliothèqe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cl.show(content, listContent[1]);
//                actualiserInformations();

            }
        });

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Onglet Statistiques
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel card3 = new JPanel();
        card3.setBackground(Color.BLUE);
        JButton statistiques = new JButton(new ImageIcon("Design/Boutons/MesStat.png"));

        JLabel txtStatistiques = new JLabel();
        txtStatistiques.setText("<html><body><font color='white'>Statistiques</body></html>");
        txtStatistiques.setToolTipText(txtStatistiques.getText());
        txtStatistiques.setFont(font);
        card3.add(txtStatistiques);
        statistiques.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cl.show(content, listContent[2]);
            }
        });

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                  Finalisation  
         *-------------------------------------------------------------------------------------------------------
         */

        content.setLayout(cl);
        content.add(card1, listContent[0]);
        content.add(card2, listContent[1]);
        content.add(card3, listContent[2]);

        lesOnglets.add(ajouterMusique);
        lesOnglets.add(musiqueEnCours);
        lesOnglets.add(bibliothèqe);
        lesOnglets.add(statistiques);

        monPanel.add(lesOnglets, BorderLayout.NORTH);
        monPanel.add(content, BorderLayout.CENTER);
    }

    public void coteEst() {

        JPanel est = new JPanel();
        est.setLayout(new BoxLayout(est, BoxLayout.PAGE_AXIS));

        Font fontBoldG = new Font("Times New Roman", Font.BOLD, 24);
        Font fontBold = new Font("Times New Roman", Font.PLAIN, 16);

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                          Titre
         *-------------------------------------------------------------------------------------------------------
         */

        actualiserPanelCoteEst(titre, auteur, duree, album, fontBold, fontBoldG);
        //titre.setText("  Titre:  \n" );
        //titre.setFont(fontBoldG);
        //titre.setText(operations.getTitre());
        est.add(titre);

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                          Photo
         *-------------------------------------------------------------------------------------------------------
         */

        JLabel img = new JLabel(new ImageIcon("Design/Boutons/imgSon.png"));
        est.add(img);


        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Informations musique
         *-------------------------------------------------------------------------------------------------------
         */


        //auteur.setText("  Auteur:  " );
        //auteur.setFont(fontBold);
        //auteur.setText(operations.getAuteur());
        est.add(auteur);

        //duree.setText("  Durée:  " );
        //duree.setFont(fontBold);
        //duree.setText(operations.getDuree());
        est.add(duree);

        //album.setText("  Album:  " );
        //album.setFont(fontBold);
        //album.setText(operations.getAlbum());
        est.add(album);


        this.monPanel.add(est, BorderLayout.EAST);
        System.out.println("titre: " + operations.getTitre() + " " + operations.getAlbum());
    }

    private void actualiserPanelCoteEst(JLabel titre, JLabel auteur, JLabel duree, JLabel album, Font fontBold, Font fontBoldG) {
        titre.setFont(fontBoldG);
        titre.setText(operations.getTitre());
        auteur.setFont(fontBold);
        auteur.setText(operations.getAuteur());
        duree.setFont(fontBold);
        duree.setText(operations.getDuree());
        album.setFont(fontBold);
        album.setText(operations.getAlbum());

    }


    private void afficherVolume() {
        StringBuilder barresVolume = new StringBuilder();
        for (int i = 0; i < this.operations.getVolume(); i++) {
            barresVolume.append('|');
        }
        this.affichageVolume.setText(barresVolume.toString());
    }

    @Override
    public void actualiserInformations() {
        afficherVolume();
        lesOnglets();
        actualiserPanelCoteEst(titre, auteur, duree, album, new Font("Times New Roman", Font.PLAIN, 16), new Font("Times New Roman", Font.BOLD, 24));
        this.monPanel.revalidate();
        System.out.println("mis a jour du panel");

    }
}
