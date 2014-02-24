/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controller.Operations;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import model.Observateur;


/**
 *
 * @author ilanmalka
 */
public class DesignMP3 extends Applet implements Observateur{

    private JPanel monPanel = new JPanel();
    private Operations operations ;
    private JLabel affichageVolume;
    private JLabel auteur;
    private JLabel duree;
    private JLabel album;
    private JLabel titre;
    
    public DesignMP3(){
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
        
        JButton precedent = new JButton (new ImageIcon("Design/Boutons/precedent.png"));
        JButton lecture = new JButton(new ImageIcon("Design/Boutons/lecture.png"));
        JButton suivant = new JButton(new ImageIcon("Design/Boutons/suivant.png"));
        JButton diminuer = new JButton(new ImageIcon("Design/Boutons/baisserSon.png"));
        JButton augmenter = new JButton(new ImageIcon("Design/Boutons/augmenterSon.png"));
        
        lesBoutons.add(precedent);
        lesBoutons.add(lecture);
        lesBoutons.add(suivant);
        lesBoutons.add(diminuer);
        lesBoutons.add(augmenter);
        lesBoutons.add(affichageVolume);
        
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
        panneauBas.add(affichageVolume);

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
        Font font = new Font("Courier",Font.BOLD,26);
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
                operations.ouvrirFenetre();
                actualiserInformations();
            }
        });
        

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                     Onglet Musique en cours
         *-------------------------------------------------------------------------------------------------------
         */

        final JPanel card1 = new JPanel();
        card1.setBackground(Color.blue);
        
        JButton musiqueEnCours= new JButton(new ImageIcon("Design/Boutons/lectureEnCours.png")); 
        
        JLabel txtMusiqueEnCours = new JLabel();
        txtMusiqueEnCours.setText("<html><body><font color='white'>Musique en cours</body></html>" );
        txtMusiqueEnCours.setToolTipText(txtMusiqueEnCours.getText() );
        txtMusiqueEnCours.setFont(font);
        card1.add(txtMusiqueEnCours);
        musiqueEnCours.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent event){               
                cl.show(content, listContent[0]);
             }
        });
        
        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Onglet Bibliothèque
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel card2 = new JPanel();
        card2.setBackground(Color.BLUE);
        
        JButton bibliothèqe= new JButton(new ImageIcon("Design/Boutons/MaBibli.png"));
        
        JLabel txtBibliothèque = new JLabel();
        txtBibliothèque.setText("<html><body><font color='white'>Bibliothèque</body></html>" );
        txtBibliothèque.setToolTipText(txtBibliothèque.getText() );
        txtBibliothèque.setFont(font);
        card2.add(txtBibliothèque);
        bibliothèqe.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent event){               
                cl.show(content, listContent[1]);
             }
        });

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Onglet Statistiques
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel card3 = new JPanel();
        card3.setBackground(Color.BLUE);
        JButton statistiques= new JButton(new ImageIcon("Design/Boutons/MesStat.png")); 
        
        JLabel txtStatistiques = new JLabel();
        txtStatistiques.setText("<html><body><font color='white'>Statistiques</body></html>" );
        txtStatistiques.setToolTipText(txtStatistiques.getText() );
        txtStatistiques.setFont(font);
        card3.add(txtStatistiques);
        statistiques.addActionListener(new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent event){               
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
        Font fontBold = new Font("Times New Roman", Font.PLAIN,16);

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
        for (int i=0 ; i< this.operations.getVolume() ; i++) {
            barresVolume.append('|');
        }
        this.affichageVolume.setText(barresVolume.toString());
    }
    
    
    @Override
    public void actualiserInformations() {
        afficherVolume();
        actualiserPanelCoteEst(titre, auteur, duree, album, new Font("Times New Roman", Font.PLAIN,16), new Font("Times New Roman", Font.BOLD, 24));
        this.monPanel.revalidate();
        System.out.println("mis a jour du panel");
        
    }

}
