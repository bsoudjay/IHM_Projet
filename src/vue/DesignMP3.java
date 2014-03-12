/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import model.Sound;
import controller.*;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Bibliotheque;
import model.Observateur;

/**
 *
 * @author ilanmalka
 */
public class DesignMP3 extends Applet implements Observateur {

    private JPanel monPanel = new JPanel();
    private final JSlider slide = new JSlider();
    public JSlider barreMusique = new JSlider();
    private Operations operations;
    private JLabel affichageVolume;
    private JLabel auteur;
    private JLabel duree;
    private JLabel album;
    private JLabel titre;
    private JLabel annee;
    private JLabel qualite;
    private JLabel genre;
    private JLabel tempsTotal;
    private JLabel tempsRestant;
    private Sound monSon;
    private int enCoursDeLecture = 0;
    private Bibliotheque biblio;
    public Thread threadLecture;
    public JPanel maBibli;

    public DesignMP3() {

        this.biblio = new Bibliotheque("test");
        this.maBibli = new JPanel();

        GridLayout gl = new GridLayout(20, 1);
        gl.setHgap(5); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
        gl.setVgap(5);
        this.maBibli.setLayout(gl);

        try {
            this.operations = new Operations();
        } catch (SQLException ex) {
            Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.operations.ajouterObservateur(this);
        this.affichageVolume = new JLabel();
        this.auteur = new JLabel();
        this.duree = new JLabel();
        this.album = new JLabel();
        this.titre = new JLabel();
        this.annee = new JLabel();
        this.genre = new JLabel();
        this.qualite = new JLabel();
        this.tempsTotal = new JLabel("Temps total");
        this.tempsRestant = new JLabel("Temps restant");
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
        JPanel barreMusiq = new JPanel();
        // barreMusique.setSize(400, 400);
        barreMusiq.setLayout((new BoxLayout(barreMusiq, BoxLayout.LINE_AXIS)));
        barreMusiq.add(tempsRestant);

        JPanel barreSon = new JPanel();
        //barreSon.setSize(2, 20);

        this.barreMusique.setMaximum(100);
        this.barreMusique.setMinimum(0);
        this.barreMusique.setValue(0);
        this.barreMusique.setPaintTicks(false);
        this.barreMusique.setPaintLabels(false);

        barreMusiq.add(this.barreMusique);
        barreMusiq.add(tempsTotal);

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


        precedent.setOpaque(false);
        precedent.setContentAreaFilled(false);
        precedent.setBorderPainted(false);

        lecture.setOpaque(false);
        lecture.setContentAreaFilled(false);
        lecture.setBorderPainted(false);

        suivant.setOpaque(false);
        suivant.setContentAreaFilled(false);
        suivant.setBorderPainted(false);

        diminuer.setOpaque(false);
        diminuer.setContentAreaFilled(false);
        diminuer.setBorderPainted(false);

        augmenter.setOpaque(false);
        augmenter.setContentAreaFilled(false);
        augmenter.setBorderPainted(false);

        lesBoutons.add(precedent);
        lesBoutons.add(lecture);
        lesBoutons.add(suivant);
        lesBoutons.add(diminuer);
        lesBoutons.add(augmenter);

        //lesBoutons.add(this.affichageVolume);
        //barreSon.setSize(20, 2);

        slide.setMaximum(100);
        slide.setMinimum(0);
        slide.setValue(50);
        slide.setPaintTicks(true);
        slide.setPaintLabels(true);
        slide.setMinorTickSpacing(10);
        slide.setMajorTickSpacing(20);
        slide.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
            }
        });
        barreSon.add(slide);
        lesBoutons.add(barreSon);



        augmenter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                slide.setValue((slide.getValue() + 10));
                System.out.println(slide.getValue());
                actualiserInformations();
            }
        });

        diminuer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                slide.setValue((slide.getValue() - 10));
                System.out.println(slide.getValue());
                actualiserInformations();
            }
        });

        lecture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // operations.ajouterBDD();
                threadLecture = new Thread(new PlaySound());
                threadLecture.start();
                actualiserInformations();
            }
        });

//        diminuer.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                operations.diminuerVolume();
//                actualiserInformations();
//            }
//        });
//
//        augmenter.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//
//                actualiserInformations();
//                operations.augmenterVolume();
//            }
//        });
        //lesBoutons.add(new JButton("barre Son"));

        /*
         *-------------------------------------------------------------------------------------------------------
         *                          Création du BoxLayout -- superposition des icones  
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel panneauBas = new JPanel();
        panneauBas.setLayout((new BoxLayout(panneauBas, BoxLayout.PAGE_AXIS)));
        panneauBas.add(barreMusiq);
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



        ajouterMusique.setOpaque(false);
        ajouterMusique.setContentAreaFilled(false);
        ajouterMusique.setBorderPainted(false);



        ajouterMusique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                try {
                    operations.ouvrirFenetre();
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (enCoursDeLecture == 1) {
                    try {
                        threadLecture.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    enCoursDeLecture = 0;
                }

                try {
                    biblio.recupererMusique();
//             actualiserInformations();
                } catch (SQLException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }

                actualiserBiblio(biblio);
                actualiserInformations();
                monPanel.revalidate();

            }
        });


        /*
         *-------------------------------------------------------------------------------------------------------
         *                                     Onglet Musique en cours
         *-------------------------------------------------------------------------------------------------------
         */
        final JPanel card1 = new JPanel();
        card1.setBackground(Color.RED);//kvn image

        JButton musiqueEnCours = new JButton(new ImageIcon("Design/Boutons/lectureEnCours.png"));

        musiqueEnCours.setOpaque(false);
        musiqueEnCours.setContentAreaFilled(false);
        musiqueEnCours.setBorderPainted(false);

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
        card2.setBackground(Color.GRAY);

        JButton bibliothèqe = new JButton(new ImageIcon("Design/Boutons/MaBibli.png"));

        bibliothèqe.setOpaque(false);
        bibliothèqe.setContentAreaFilled(false);
        bibliothèqe.setBorderPainted(false);



        JLabel txtBibliothèque = new JLabel();
        txtBibliothèque.setLayout(new BoxLayout(txtBibliothèque, BoxLayout.LINE_AXIS));
        txtBibliothèque.setText("<html><body><font color='white'>Bibliothèque</body></html>");
        txtBibliothèque.setToolTipText(txtBibliothèque.getText());
        txtBibliothèque.setFont(font);
        card2.add(txtBibliothèque);

        Font fontBold = new Font("Times New Roman", Font.PLAIN, 16);

        maBibli.setFont(fontBold);

        JPanel bibli = new JPanel();
        bibli.setLayout(new BoxLayout(bibli, BoxLayout.PAGE_AXIS));

        bibli.add(txtBibliothèque);
        bibli.add(maBibli);

        card2.add(bibli);

        bibliothèqe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cl.show(content, listContent[1]);

                try {
                    biblio.recupererMusique();
//             actualiserInformations();
                } catch (SQLException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                actualiserBiblio(biblio);
                actualiserInformations();
                monPanel.revalidate();

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

        statistiques.setOpaque(false);
        statistiques.setContentAreaFilled(false);
        statistiques.setBorderPainted(false);



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
         *                                      Barre de recherche
         *-------------------------------------------------------------------------------------------------------
         */
        final JTextField recherche = new JTextField("Recherche");

        recherche.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                recherche.setText("");
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
        lesOnglets.add(recherche);

        monPanel.add(lesOnglets, BorderLayout.NORTH);
        monPanel.add(content, BorderLayout.CENTER);
    }

    public void coteEst() {

        JPanel est = new JPanel();
        est.setLayout(new BoxLayout(est, BoxLayout.PAGE_AXIS));
        est.setPreferredSize(new Dimension(250, 1000));

        Font fontBoldG = new Font("Times New Roman", Font.BOLD, 24);
        Font fontBold = new Font("Times New Roman", Font.PLAIN, 16);

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                          Titre
         *-------------------------------------------------------------------------------------------------------
         */
        actualiserPanelCoteEst(titre, auteur, duree, album, annee, genre, qualite, fontBold, fontBoldG);
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
        est.add(auteur);
        est.add(duree);
        est.add(album);
        est.add(annee);
        est.add(genre);
        est.add(qualite);

        this.monPanel.add(est, BorderLayout.EAST);
    }

    class PlaySound implements Runnable {

        @Override
        public void run() {
//            enCoursDeLecture = 1;
//            System.out.println("totototototto");
//            if (monSon.isPlaying()) {
//                barreMusique.setValue((barreMusique.getValue() + 1));
//                System.out.println("j'avance avec la musique");
//            }
            operations.lire();
            actualiserInformations();
            System.out.println("thread");
        }
    }

    private void actualiserPanelCoteEst(JLabel titre, JLabel auteur, JLabel duree, JLabel album, JLabel annee, JLabel genre, JLabel qualite, Font fontBold, Font fontBoldG) {
        titre.setFont(fontBoldG);
        titre.setText(operations.getTitre());
        auteur.setFont(fontBold);
        auteur.setText("Auteur : " + operations.getAuteur());
        duree.setFont(fontBold);
        duree.setText("Durée : " + operations.getDuree());
        album.setFont(fontBold);
        album.setText("Album : " + operations.getAlbum());
        annee.setFont(fontBold);
        annee.setText("Année : " + operations.getAnnee());
        genre.setFont(fontBold);
        genre.setText("Genre : " + operations.getGenre());
        qualite.setFont(fontBold);
        qualite.setText("Qualité : " + operations.getQualite());
    }

    private void actualiserBiblio(Bibliotheque b) {
        System.out.println("actualiserBiblio(Bibliotheque b)");
        maBibli.removeAll();

        ArrayList<String> test = new ArrayList<String>();
        test = biblio.label();

        for (int i = 0; i < test.size(); i++) {

            System.out.println("sa marche");
            maBibli.add(new JButton(test.get(i)));

        }

    }

    private void modifVolume() {
        this.slide.setValue(this.slide.getValue());
    }

    private void modifBarreSon() {
        this.barreMusique.setValue(this.barreMusique.getValue());
    }

//    private void afficherVolume() {
//        StringBuilder barresVolume = new StringBuilder();
//        for (int i = 0; i < this.operations.getVolume(); i++) {
//            barresVolume.append('|');
//        }
//        this.affichageVolume.setText(barresVolume.toString());
//    }
    @Override
    public void actualiserInformations() {
        modifBarreSon();
        modifVolume();
        //afficherVolume();
        System.out.println("zerty");

        this.barreMusique.setValue(WIDTH);
        tempsTotal.setText(operations.getDuree());
        actualiserPanelCoteEst(titre, auteur, duree, album, annee, genre, qualite, new Font("Times New Roman", Font.PLAIN, 16), new Font("Times New Roman", Font.BOLD, 24));
        this.monPanel.revalidate();
        System.out.println("mis a jour du panel");

    }
}
