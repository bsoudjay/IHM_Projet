/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import vue.*;
import controller.*;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javazoom.jl.decoder.JavaLayerException;
import model.Bibliotheque;
import model.Musique;
import model.Observateur;
import model.Sound;
import model.Statistiques;
import static sun.audio.AudioPlayer.player;

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
    private int enCoursDeLecture = 0;
    private Bibliotheque biblio;
    public Thread threadLecture;
    public JPanel maBibli;
    public ArrayList<Musique> test;
    public ArrayList<Musique> statsAfiiche;
    public ArrayList<String> test2;
    public int i;
    public int jstats;
    private JPanel card1 = new JPanel();
    private Statistiques stats;
    private Sound s = new Sound();
    private Sound s2 = new Sound();
    public JPanel maStats;
    private String contenuRecherche;
    private JLabel img;

    public DesignMP3() throws Exception {

        this.img = new JLabel();
        this.img.setSize(50, 50);
        this.maStats = new JPanel();

        this.stats = new Statistiques("test");
        this.i = 0;
        this.test = new ArrayList<Musique>();
        this.biblio = new Bibliotheque("test");
        this.maBibli = new JPanel();

        GridLayout gl = new GridLayout(20, 1);
        gl.setHgap(5); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
        gl.setVgap(5);
        this.maStats.setLayout(gl);
        this.maBibli.setLayout((new BoxLayout(maBibli, BoxLayout.PAGE_AXIS)));

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
        ImagePanel barreMusiq = new ImagePanel("bas");
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
        //lesBoutons.setLayout((new BoxLayout(lesBoutons, BoxLayout.LINE_AXIS)));

        JButton stop = new JButton(new ImageIcon("Design/Boutons/stop.png"));
        JButton precedent = new JButton(new ImageIcon("Design/Boutons/precedent.png"));
        JButton lecture = new JButton(new ImageIcon("Design/Boutons/lecture.png"));
        JButton suivant = new JButton(new ImageIcon("Design/Boutons/suivant.png"));
        JButton diminuer = new JButton(new ImageIcon("Design/Boutons/baisserSon.png"));
        JButton augmenter = new JButton(new ImageIcon("Design/Boutons/augmenterSon.png"));

        stop.setOpaque(false);
        stop.setContentAreaFilled(false);
        stop.setBorderPainted(false);

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

        lesBoutons.add(stop);
        lesBoutons.add(precedent);
        lesBoutons.add(lecture);
        lesBoutons.add(suivant);
        lesBoutons.add(diminuer);

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
                setControlFromSlider();
            }
        });
        barreSon.add(slide);
        lesBoutons.add(barreSon);

        lesBoutons.add(augmenter);

        augmenter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                slide.setValue((slide.getValue() + 3));
                System.out.println(slide.getValue());
                /*try {
                 operations.augmenterSon();
                 } catch (LineUnavailableException ex) {
                 Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
                actualiserInformations();
            }
        });

        diminuer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                slide.setValue((slide.getValue() - 3));
                System.out.println(slide.getValue());
                /*try {
                 operations.diminuerSon();
                 } catch (LineUnavailableException ex) {
                 Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                 }*/
                actualiserInformations();
            }
        });

        lecture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // operations.ajouterBDD();
                if (s.isPlaying()) {
                    threadLecture.stop();
                } else if (s2.isPlaying()) {
                    threadLecture.stop();
                }
                String tmp = operations.reecouterMusic(titre.getText());
                try {
                    s = new Sound(tmp);
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                threadLecture = new Thread(new PlaySound());
                threadLecture.start();
                s2.setIsPlaying(true);
                actualiserInformations();
            }
        });

        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (s.isPlaying()) {
                    threadLecture.stop();
                    System.out.println("tuage de thread");
                    s.setIsPlaying(false);
                } else if (s2.isPlaying()) {
                    System.out.println("tuage de thread");
                    threadLecture.stop();
                    s2.setIsPlaying(false);
                }
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
         *                          Cr��ation du BoxLayout -- superposition des icones  
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel panneauBas = new JPanel();
        panneauBas.setLayout((new BoxLayout(panneauBas, BoxLayout.PAGE_AXIS)));
        panneauBas.add(barreMusiq);
        panneauBas.add(lesBoutons);

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                  Cr��ation du BorderLayout  
         *-------------------------------------------------------------------------------------------------------
         */
        monPanel.add(panneauBas, BorderLayout.SOUTH);
    }

    public void lesOnglets() {

        final CardLayout cl = new CardLayout();
        ImagePanel lesOnglets = new ImagePanel("haut");
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
//                if (enCoursDeLecture == 1) {
//                    try {
//                        threadLecture.sleep(1000);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                    enCoursDeLecture = 0;
//                }

                try {
                    biblio.recupererMusique();
//                  actualiserInformations();
                } catch (SQLException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
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
//        final JPanel card1 = new JPanel();
//        card1.setBackground(Color.GRAY);//kvn image
//
//        JButton musiqueEnCours = new JButton(new ImageIcon("Design/Boutons/lectureEnCours.png"));
//
//        musiqueEnCours.setOpaque(false);
//        musiqueEnCours.setContentAreaFilled(false);
//        musiqueEnCours.setBorderPainted(false);
        
        JButton musiqueEnCours = new JButton(new ImageIcon("Design/Boutons/lectureEnCours.png"));

        musiqueEnCours.setOpaque(false);
        musiqueEnCours.setContentAreaFilled(false);
        musiqueEnCours.setBorderPainted(false);

//        JLabel txtMusiqueEnCours = new JLabel();
//        txtMusiqueEnCours.setText("<html><body><font color='white'>Musique en cours</body></html>");
//        txtMusiqueEnCours.setToolTipText(txtMusiqueEnCours.getText());
//        txtMusiqueEnCours.setFont(font);
//        card1.add(txtMusiqueEnCours);
//        JLabel textaccueil = new JLabel();
//        PanelTout t = new PanelTout("C:\\Users\\kevin\\Pictures\\1.jpg");
//        System.out.println(t.getImg());
//        textaccueil.setText("<html><body><font color='white'>------------------------------------------MUSIQUE EN COUR--------------------------------------------</body></html>");
//        textaccueil.setToolTipText(textaccueil.getText());
//        textaccueil.setFont(font);

//        JPanel accueil = new JPanel();
//        accueil.setLayout(new BoxLayout(accueil, BoxLayout.PAGE_AXIS));
//        accueil.add(textaccueil);
//        accueil.add(t );
        card1.setBackground(Color.GRAY);
         String img=operations.Diapo();
        System.out.println(img);
        JLabel lab = new JLabel(new ImageIcon(img));
        lab.setBounds(0, 0, 1, 1);
        card1.add(lab);
        musiqueEnCours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                card1.removeAll();
        card1.setBackground(Color.GRAY);
                cl.show(content, listContent[0]);
                String img=operations.Diapo();
        System.out.println(img);
        JLabel lab = new JLabel(new ImageIcon(img));
        lab.setBounds(0, 0, 1, 1);
//        JLabel image = new JLabel(new ImageIcon("C:\\Users\\kevin\\Pictures\\1.jpg"));
//        this.setLayout(new BorderLayout());
//        this.add(image, BorderLayout.CENTER);

        card1.add(lab);
            }
        });

        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Onglet Biblioth��que
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel card2 = new JPanel();
        JScrollPane scroll = new JScrollPane(card2);
        card2.setBackground(Color.GRAY);

        JButton bibliotheqe = new JButton(new ImageIcon("Design/Boutons/MaBibli.png"));

        bibliotheqe.setOpaque(false);
        bibliotheqe.setContentAreaFilled(false);
        bibliotheqe.setBorderPainted(false);

        JLabel txtBibliotheque = new JLabel();
        txtBibliotheque.setLayout(new BoxLayout(txtBibliotheque, BoxLayout.LINE_AXIS));
        txtBibliotheque.setText("<html><body><font color='white'>--------------------------------------------------BIBLIOTHEQUE------------------------------------------------</body></html>");
        txtBibliotheque.setToolTipText(txtBibliotheque.getText());
        txtBibliotheque.setFont(font);
        card2.add(txtBibliotheque);

        Font fontBold = new Font("Times New Roman", Font.PLAIN, 16);

        maBibli.setFont(fontBold);

        JPanel bibli = new JPanel();
        bibli.setLayout(new BoxLayout(bibli, BoxLayout.PAGE_AXIS));

        bibli.add(txtBibliotheque);
        //maBibli.setLayout(new GridLayout(20, 3));
        bibli.add(maBibli);

        card2.add(bibli);

        bibliotheqe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cl.show(content, listContent[1]);

                try {
                    biblio.recupererMusique();
//             actualiserInformations();
                } catch (SQLException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
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
        card3.setBackground(Color.GRAY);
        JButton statistiques = new JButton(new ImageIcon("Design/Boutons/MesStat.png"));

        statistiques.setOpaque(false);
        statistiques.setContentAreaFilled(false);
        statistiques.setBorderPainted(false);

        JLabel txtStatistiques = new JLabel();
        txtStatistiques.setText("<html><body><font color='white'>----------------------------------------------STATISTIQUES------------------------------------------------</body></html>");
        txtStatistiques.setToolTipText(txtStatistiques.getText());
        txtStatistiques.setFont(font);

        JPanel stat = new JPanel();
        stat.setLayout(new BoxLayout(stat, BoxLayout.PAGE_AXIS));
        stat.add(txtStatistiques);
        stat.add(maStats);

        card3.add(stat);
        statistiques.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cl.show(content, listContent[2]);

                try {
                    stats.recupererGenre();
//             actualiserInformations();
                } catch (SQLException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                actualiserStats(stats);
                actualiserInformations();
                monPanel.revalidate();
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
        content.add(scroll, listContent[1]);
        content.add(card3, listContent[2]);

        lesOnglets.add(ajouterMusique);
        lesOnglets.add(musiqueEnCours);
        lesOnglets.add(bibliotheqe);
        lesOnglets.add(statistiques);
        lesOnglets.add(recherche);

        JButton research = new JButton(new ImageIcon("Design/Boutons/recherche.png"));
        research.setOpaque(false);
        research.setContentAreaFilled(false);
        research.setBorderPainted(false);

        lesOnglets.add(research);

        research.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("debut recherhce");
                contenuRecherche = recherche.getText();
                ArrayList<Musique> a = new ArrayList<Musique>();
                a = operations.recherche(contenuRecherche);
                for (Musique c : a) {
                    System.out.println("dedans");
                    System.out.println(c);
                }
            }
        });

        monPanel.add(lesOnglets, BorderLayout.NORTH);
        monPanel.add(content, BorderLayout.CENTER);
    }

    public void coteEst() {

        //  JPanel est = new JPanel();
        ImagePanel est = new ImagePanel("est");
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
        
        
        ImageIcon icon = new ImageIcon("Design/Boutons/imgSon.png");
        img.setIcon(icon);
        
        est.add(img);


        /*
         *-------------------------------------------------------------------------------------------------------
         *                                      Informations musique
         *-------------------------------------------------------------------------------------------------------
         */
        JPanel informations = new JPanel();
        informations.setLayout(new BoxLayout(informations, BoxLayout.PAGE_AXIS));

        informations.add(auteur);
        informations.add(duree);
        informations.add(album);
        informations.add(annee);
        informations.add(genre);
        informations.add(qualite);

        est.add(informations);

        this.monPanel.add(est, BorderLayout.EAST);
    }

    class PlaySound implements Runnable {

        private volatile boolean cancelled;

        @Override
        public void run() {
            try {
                //            enCoursDeLecture = 1;
//            System.out.println("totototototto");
//            if (monSon.isPlaying()) {
//                barreMusique.setValue((barreMusique.getValue() + 1));
//                System.out.println("j'avance avec la musique");
//            }

                s.player.play();
            } catch (JavaLayerException ex) {
                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public void cancel() {
            cancelled = true;
        }
    }

    class PlaySoundBouton implements Runnable {

        private Sound s;

        public PlaySoundBouton(Sound s) {
            this.s = s;
        }

        public void lireMusique() throws Exception {
            this.s.play();
            actualiserInformations();
        }

        @Override
        public void run() {
            try {
                lireMusique();
                System.out.println("ca roule => lecture en cours");
            } catch (Exception ex) {
                System.out.println("la grosse merdee -> veut pas lire");
            }
            actualiserInformations();
            System.out.println("thread");
        }
    }

    private void actualiserPanelCoteEst(JLabel titre, JLabel auteur, JLabel duree, JLabel album, JLabel annee, JLabel genre, JLabel qualite, Font fontBold, Font fontBoldG) {
        titre.setFont(fontBoldG);
        titre.setText(operations.getTitre());
        titre.setPreferredSize(new Dimension(230, 40));

        auteur.setFont(fontBold);
        auteur.setText("Auteur : " + operations.getAuteur());
        auteur.setPreferredSize(new Dimension(230, 40));

        duree.setFont(fontBold);
        duree.setText("Durée : " + operations.getDuree());
        duree.setPreferredSize(new Dimension(230, 40));

        album.setFont(fontBold);
        album.setText("Album : " + operations.getAlbum());
        album.setPreferredSize(new Dimension(230, 40));

        annee.setFont(fontBold);
        annee.setText("Année : " + operations.getAnnee());
        annee.setPreferredSize(new Dimension(230, 40));

        genre.setFont(fontBold);
        genre.setText("Genre : " + operations.getGenre());
        genre.setPreferredSize(new Dimension(230, 40));

        qualite.setFont(fontBold);
        qualite.setText("Qualité : " + operations.getQualite());
        qualite.setPreferredSize(new Dimension(230, 20));
    }

    private void actualiserBiblio(Bibliotheque b) {

        maBibli.removeAll();
        /* ArrayList<String> test = new ArrayList<String>();
         test = operations.bibliothequeComplete();
        
         for (int i = 0; i < test.size(); i += 3) {
         maBibli.add(new JLabel(" "));
         maBibli.add(new JButton(test.get(i)));
         maBibli.add(new JLabel("Artiste: " + test.get(i + 1)));
         maBibli.add(new JLabel("Duree: " + test.get(i + 2)));
         maBibli.add(new JLabel("  ___________________________________ "));
         }
         */

        test = biblio.label();

        for (i = 0; i < test.size(); i++) {

            final JButton bou = new JButton(test.get(i).getTitre());

            bou.setIconTextGap(i);
            bou.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    operations.setTitre(test.get(bou.getIconTextGap()).getTitre());
                    operations.setAuteur(test.get(bou.getIconTextGap()).getAuteur());
                    operations.setAlbum(test.get(bou.getIconTextGap()).getAlbum());

                    operations.setDuree(test.get(bou.getIconTextGap()).getDuree());
                    operations.setGenre(test.get(bou.getIconTextGap()).getGenre());
                    operations.setChemin(test.get(bou.getIconTextGap()).getChemin());

                    
                    try {
                     
                        operations.setImage(test.get(bou.getIconTextGap()).getChemin());
                    } catch (IOException ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedTagException ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidDataException ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    String chemin2 = operations.reecouterMusic(test.get(bou.getIconTextGap()).getTitre());
                    try {
                        if (s.isPlaying()) {
                            threadLecture.stop();
                        } else if (s2.isPlaying()) {
                            threadLecture.stop();
                        }
                        s = new Sound(chemin2);
                        threadLecture = new Thread(new PlaySoundBouton(s));
                        threadLecture.start();
                        s.setIsPlaying(true);
                    } catch (Exception ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    actualiserImage();
                    actualiserInformations();
                }
            });

            maBibli.add(bou);
            maBibli.add(new JLabel("Artiste: " + test.get(bou.getIconTextGap()).getAuteur()));
            long duration = test.get(bou.getIconTextGap()).getDuree();
            maBibli.add(new JLabel("Duree: " + (String.format("%02d:%02d:%02d",
                    TimeUnit.MICROSECONDS.toHours(duration) - ((int) TimeUnit.MICROSECONDS.toDays(duration) * 24),
                    TimeUnit.MICROSECONDS.toMinutes(duration) - (TimeUnit.MICROSECONDS.toHours(duration) * 60),
                    TimeUnit.MICROSECONDS.toSeconds(duration) - (TimeUnit.MICROSECONDS.toMinutes(duration) * 60)))));
            maBibli.add(new JLabel(" _______________________________"));

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
 
      
        this.barreMusique.setValue(WIDTH);
        tempsTotal.setText(operations.getDuree());
        actualiserPanelCoteEst(titre, auteur, duree, album, annee, genre, qualite, new Font("Times New Roman", Font.PLAIN, 16), new Font("Times New Roman", Font.BOLD, 24));
        this.monPanel.revalidate();
        System.out.println("mis a jour du panel");

    }

    private void actualiserStats(Statistiques s) {
        maStats.removeAll();
        /* ArrayList<String> test = new ArrayList<String>();
         test = operations.bibliothequeComplete();
        
         for (int i = 0; i < test.size(); i += 3) {
         maBibli.add(new JLabel(" "));
         maBibli.add(new JButton(test.get(i)));
         maBibli.add(new JLabel("Artiste: " + test.get(i + 1)));
         maBibli.add(new JLabel("Duree: " + test.get(i + 2)));
         maBibli.add(new JLabel("  ___________________________________ "));
         }
         */

        test2 = stats.label();
        for (jstats = 0; jstats < test2.size(); jstats++) {

            final JButton bou2 = new JButton(test2.get(jstats));
            bou2.setIconTextGap(jstats);
            bou2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String genre = (test2.get(bou2.getIconTextGap()));
                    System.out.println(genre);
                    maStats.removeAll();
                    statsAfiiche = operations.statsNbEcoute(genre);
                    statsGraphe s = new statsGraphe("Stat", "Les stats de " + genre, statsAfiiche);
                    s.pack();
                    s.setVisible(true);

                }
            });
            maStats.add(bou2);
        }

    }

    private void setControlFromSlider() {
        Line line;
        try {
            line = AudioSystem.getLine(Port.Info.SPEAKER);
            line.open();

            FloatControl gainControl
                    = (FloatControl) line.getControl(FloatControl.Type.VOLUME);

            int slider_range = slide.getMaximum() - slide.getMinimum();
            float max = gainControl.getMaximum();
            float min = gainControl.getMinimum();
            float range = max - min;
            // figure out slider percentage
            float sliderPercentage = (float) slide.getValue() / slider_range;
            // System.out.println ("new slider value = " + slider.getValue() +
            //                    ", percentage= " + sliderPercentage);
            // figure out value for that percentage of range
            float rangeOffset = sliderPercentage * range;
            float newValue = rangeOffset + min;
            // System.out.println ("rangeOffset = " + rangeOffset +
            //                     ", newValue = " + newValue);
            gainControl.setValue(newValue);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void actualiserImage() {
        
        if(operations.getPicture()==null){
            
            img.setIcon(new ImageIcon("Design/Boutons/imgSon.png"));
            
        }else{
            
            Image resizedImage = operations.getPicture().getScaledInstance(200, 200, 0);
          
            img.setIcon(new ImageIcon(resizedImage));

}
        
    }
    
}
