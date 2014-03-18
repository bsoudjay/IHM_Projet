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
import static java.awt.Component.CENTER_ALIGNMENT;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import static java.awt.Image.SCALE_SMOOTH;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
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
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.Icon;
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
    public Thread T1;
    public Thread T2;
    public JPanel maBibli;
    public JPanel maRecherche;
    public ArrayList<Musique> test;
    public ArrayList<Musique> statsAfiiche;
    public ArrayList<String> test2;
    public ArrayList<Musique> test3;
    public int i;
    public int nbRes;
    public int ibiblio;
    public int jstats;
    private JPanel card1 = new JPanel();
    private Statistiques stats;
    private Sound s = new Sound();
    private Sound s2 = new Sound();
    public JPanel maStats;
    private String contenuRecherche;
    private JLabel img;
    public Icon iconScaled;

    public JSlider getBarreMusique() {
        return this.barreMusique;
    }

    public DesignMP3() throws Exception {

        this.img = new JLabel();
        this.img.setSize(50, 50);
        this.maStats = new JPanel();

        this.stats = new Statistiques("test");
        this.i = 0;
        this.test = new ArrayList<Musique>();
        this.test3 = new ArrayList<Musique>();

        this.biblio = new Bibliotheque("test");
        this.maBibli = new JPanel();
        this.maRecherche = new JPanel();

        GridLayout gl = new GridLayout(20, 1);
        gl.setHgap(5); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
        gl.setVgap(5);
        this.maStats.setLayout(gl);
        
        GridLayout g2 = new GridLayout(i, 3);
        this.maBibli.setLayout(g2);

        GridLayout g3 = new GridLayout(nbRes, 3);
        this.maRecherche.setLayout(g3);

        //g2.setHgap(5); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
        g2.setVgap(10);
        g3.setVgap(10);

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
        
        JButton photo = new JButton(new ImageIcon("Design/Boutons/photo.png"));
        JButton stop = new JButton(new ImageIcon("Design/Boutons/stop.png"));
        JButton precedent = new JButton(new ImageIcon("Design/Boutons/precedent.png"));
        JButton lecture = new JButton(new ImageIcon("Design/Boutons/lecture.png"));
        JButton suivant = new JButton(new ImageIcon("Design/Boutons/suivant.png"));
        JButton diminuer = new JButton(new ImageIcon("Design/Boutons/baisserSon.png"));
        JButton augmenter = new JButton(new ImageIcon("Design/Boutons/augmenterSon.png"));

        photo.setOpaque(false);
        photo.setContentAreaFilled(false);
        photo.setBorderPainted(false);
        
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

        lesBoutons.add(photo);
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
        
        
        photo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    operations.ouvrirPhoto();
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

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

        precedent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tmp = null;
                if (s.isPlaying()) {
                    T1.stop();
                    T2.stop();
                } else if (s2.isPlaying()) {
                    T1.stop();
                    T2.stop();
                }
                if (ibiblio == 0) {
                    ibiblio = i - 1;
                } else {
                    ibiblio--;
                }
                String musiquePrecedent = biblio.getBiblioTitre(ibiblio);
                System.out.println(musiquePrecedent);
                tmp = operations.reecouterMusic(musiquePrecedent);
                System.out.println(tmp);
                try {
                    s = new Sound(tmp);
                    operations.ajouterBDD();
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                T1 = new Thread(new ThreadDemo("Thread-1", s));
                T1.start();

                T2 = new Thread(new ThreadDemo2("Thread-2", barreMusique, s));
                T2.start();
                s.setIsPlaying(true);
                operations.setTitre(s.getTitre());
                operations.setAuteur(s.getAuteur());
                operations.setAlbum(s.getAlbum());

                operations.setDuree(s.getDuree());
                operations.setGenre(s.getGenre());
                operations.setChemin(s.getChemin());
                try {
                    operations.setImage(s.getChemin());
                } catch (IOException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedTagException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidDataException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                actualiserImage();
                actualiserInformations();
            }
        });

        suivant.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tmp = null;
                if (s.isPlaying()) {
                    T1.stop();
                    T2.stop();
                } else if (s2.isPlaying()) {
                    T1.stop();
                    T2.stop();
                }
                if (ibiblio == i - 1) {
                    ibiblio = 0;
                } else {
                    ibiblio++;
                }
                String musiqueSuivant = biblio.getBiblioTitre(ibiblio);
                System.out.println(musiqueSuivant);
                tmp = operations.reecouterMusic(musiqueSuivant);
                System.out.println(tmp);
                try {
                    s = new Sound(tmp);
                    operations.ajouterBDD();
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                T1 = new Thread(new ThreadDemo("Thread-1", s));
                T1.start();

                T2 = new Thread(new ThreadDemo2("Thread-2", barreMusique, s));
                T2.start();
                s.setIsPlaying(true);
                operations.setTitre(s.getTitre());
                operations.setAuteur(s.getAuteur());
                operations.setAlbum(s.getAlbum());

                operations.setDuree(s.getDuree());
                operations.setGenre(s.getGenre());
                operations.setChemin(s.getChemin());
                try {
                    operations.setImage(s.getChemin());
                } catch (IOException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedTagException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvalidDataException ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                actualiserImage();
                actualiserInformations();
            }
        });

        lecture.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // operations.ajouterBDD();
                if (s.isPlaying()) {
                    T1.stop();
                    T2.stop();
                    System.out.println("azertyazertyuzerty");
                } else if (s2.isPlaying()) {
                    T1.stop();
                    T2.stop();
                }

                String tmp = operations.reecouterMusic(titre.getText());
                try {
                    s = new Sound(tmp);
                    operations.ajouterBDD();
                } catch (Exception ex) {
                    Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                }
                T1 = new Thread(new ThreadDemo("Thread-1", s));
                T1.start();

                T2 = new Thread(new ThreadDemo2("Thread-2", barreMusique, s));
                T2.start();
                s2.setIsPlaying(true);
                actualiserInformations();
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (s.isPlaying()) {
                    T1.stop();
                    T2.stop();
                } else if (s2.isPlaying()) {
                    T1.stop();
                    T2.stop();
                }
                s.setIsPlaying(false);
            }
        });

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
        final String[] listContent = {"CARD_1", "CARD_2", "CARD_3", "CARD_4"};

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
                actualiserStats(stats);
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
        String img = operations.Diapo();
        System.out.println(img);
        ImageIcon icon = new ImageIcon(img);
        Image zoom = scaleImage(icon.getImage(), 1100, 580);//taille en pixels
        iconScaled = new ImageIcon(zoom);
        final JLabel ball = new JLabel(iconScaled);
        card1.add(ball);
        musiqueEnCours.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                card1.removeAll();
                card1.setBackground(Color.GRAY);
                cl.show(content, listContent[0]);
                String img = operations.Diapo();
                System.out.println(img);
        ImageIcon icon = new ImageIcon(img);
        Image zoom = scaleImage(icon.getImage(), 1100, 580);//taille en pixels
        iconScaled = new ImageIcon(zoom);
        final JLabel ball = new JLabel(iconScaled);
        card1.add(ball);
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

        txtBibliotheque.setText("                     BIBLIOTHEQUE                                                         ");
        txtBibliotheque.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        txtBibliotheque.setToolTipText(txtBibliotheque.getText());
        txtBibliotheque.setFont(font);
        card2.add(txtBibliotheque);

        Font fontBold = new Font("Times New Roman", Font.PLAIN, 16);

        maBibli.setFont(fontBold);

        JPanel bibli = new JPanel();
        bibli.setLayout(new BoxLayout(bibli, BoxLayout.PAGE_AXIS));

        bibli.add(txtBibliotheque);

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
        txtStatistiques.setText("                                                       STATISTIQUES                                                       ");
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
        JPanel card4 = new JPanel();
        card4.setBackground(Color.GRAY);
        JButton researchOnglet = new JButton(new ImageIcon("Design/Boutons/recherche.png"));
        researchOnglet.setOpaque(false);
        researchOnglet.setContentAreaFilled(false);
        researchOnglet.setBorderPainted(false);

        JLabel txtRecherche = new JLabel();
        txtRecherche.setText("                                                                       RECHERCHE                                                              ");
        txtRecherche.setToolTipText(txtRecherche.getText());
        txtRecherche.setFont(font);

        final JTextField recherche = new JTextField("Recherche");
        card4.add(txtRecherche);
        card4.add(maRecherche);

        researchOnglet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                cl.show(content, listContent[3]);

                maRecherche.removeAll();

                test3 = operations.recherche(recherche.getText());
                nbRes=test3.size();
                
                for (i = 0; i < test3.size(); i++) {

                    final JButton bou = new JButton();
                    JLabel l1 = new JLabel(test3.get(i).getTitre());
                    l1.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
                    bou.add(l1);

                    bou.setIconTextGap(i);
                    bou.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            ibiblio = bou.getComponentCount() - 1;
                            operations.setTitre(test3.get(bou.getIconTextGap()).getTitre());
                            operations.setAuteur(test3.get(bou.getIconTextGap()).getAuteur());
                            operations.setAlbum(test3.get(bou.getIconTextGap()).getAlbum());

                            operations.setDuree(test3.get(bou.getIconTextGap()).getDuree());
                            operations.setGenre(test3.get(bou.getIconTextGap()).getGenre());
                            operations.setChemin(test3.get(bou.getIconTextGap()).getChemin());
                            try {
                                operations.setQualite();
                            } catch (JavaLayerException ex) {
                                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (UnsupportedAudioFileException ex) {
                                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            try {

                                operations.setImage(test3.get(bou.getIconTextGap()).getChemin());
                            } catch (IOException ex) {
                                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (UnsupportedTagException ex) {
                                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvalidDataException ex) {
                                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            String chemin2 = operations.reecouterMusic(test3.get(bou.getIconTextGap()).getTitre());
                            try {

                                if (s.isPlaying()) {
                                    T1.stop();
                                    T2.stop();
                                } else if (s2.isPlaying()) {
                                    T1.stop();
                                    T2.stop();
                                }
                                s = new Sound(chemin2);
                                operations.ajouterBDD();
//                        threadLecture = new Thread(new PlaySoundBouton(s));
//                        threadLecture.start();
                                s.setIsPlaying(true);
                                T1 = new Thread(new ThreadDemo("Thread-1", s));
                                T1.start();

                                T2 = new Thread(new ThreadDemo2("Thread-2", barreMusique, s));
                                T2.start();
                                actualiserInformations();

                            } catch (Exception ex) {
                                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            actualiserImage();

                            actualiserInformations();
                        }
                    });

                    bou.setBorderPainted(false);
                    maRecherche.add(bou);
                    JLabel l2 = new JLabel(test3.get(bou.getIconTextGap()).getAuteur());
                    JButton bou2 = new JButton();
                    bou2.setBorderPainted(false);
                    l2.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
                    bou2.add(l2);
                    maRecherche.add(bou2);

                    long duration = test3.get(bou.getIconTextGap()).getDuree();
                    JLabel l3 = new JLabel((String.format("%02d:%02d", TimeUnit.MICROSECONDS.toMinutes(duration) - (TimeUnit.MICROSECONDS.toHours(duration) * 60),
                            TimeUnit.MICROSECONDS.toSeconds(duration) - (TimeUnit.MICROSECONDS.toMinutes(duration) * 60))));
                    JButton bou3 = new JButton();
                    l3.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
                    bou3.setBorderPainted(false);
                    bou3.add(l3);
                    maRecherche.add(bou3);

                }

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
        content.add(card4, listContent[3]);

        lesOnglets.add(ajouterMusique);
        lesOnglets.add(musiqueEnCours);
        lesOnglets.add(bibliotheqe);
        lesOnglets.add(statistiques);
        lesOnglets.add(recherche);

        lesOnglets.add(researchOnglet);

        researchOnglet.addActionListener(new ActionListener() {
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
        est.add(titre, CENTER_ALIGNMENT);

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
        informations.setOpaque(false);

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

//    class PlaySoundBouton implements Runnable {
//
//        private Sound s;
//
//        public PlaySoundBouton(Sound s) {
//            this.s = s;
//        }
//
//        public void lireMusique() throws Exception {
////            bougerBarreMusique();
////            this.s.play();
//             if (s.isPlaying()) {
//                    threadLecture.stop();
//                } else if (s2.isPlaying()) {
//                    threadLecture.stop();
//
//                }
//            ThreadDemo T1 = new ThreadDemo("Thread-1", s);
//            T1.start();
//
//            ThreadDemo2 T2 = new ThreadDemo2("Thread-2", barreMusique, s);
//            T2.start();
//            actualiserInformations();
//        }
//
//        @Override
//        public void run() {
//            try {
//                lireMusique();
//                System.out.println("ca roule => lecture en cours");
//            } catch (Exception ex) {
//                System.out.println("la grosse merdee -> veut pas lire");
//            }
//            actualiserInformations();
//            System.out.println("thread");
//        }
//    }
    public static Image scaleImage(Image source, int width, int height) {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) img.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, null);
        g.dispose();
        return img;
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

        JButton trieTitre = new JButton();
        JLabel t1 = new JLabel("Trier par titre");
        t1.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
        trieTitre.add(t1);
        trieTitre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                biblio.trierParTitre();
                actualiserBiblio(biblio);
            }
        });

        JButton trieAuteur = new JButton();
        JLabel t2 = new JLabel("Trier par auteur");
        t1.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
        trieAuteur.add(t2);
        trieAuteur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                biblio.trierParAuteur();
                actualiserBiblio(biblio);
            }
        });

        JButton trieDuree = new JButton();
        JLabel t3 = new JLabel("Trier par duree");
        t3.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
        trieDuree.add(t3);
        trieDuree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                biblio.trierParDuree();
                actualiserBiblio(biblio);
            }
        });

        this.maBibli.add(trieTitre);
        this.maBibli.add(trieAuteur);
        this.maBibli.add(trieDuree);

        test = biblio.label();

        for (i = 0; i < test.size(); i++) {

            final JButton bou = new JButton();
            JLabel l1 = new JLabel(test.get(i).getTitre());
            l1.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
            bou.add(l1);

            bou.setIconTextGap(i);
            bou.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    ibiblio = bou.getComponentCount() - 1;
                    operations.setTitre(test.get(bou.getIconTextGap()).getTitre());
                    operations.setAuteur(test.get(bou.getIconTextGap()).getAuteur());
                    operations.setAlbum(test.get(bou.getIconTextGap()).getAlbum());

                    operations.setDuree(test.get(bou.getIconTextGap()).getDuree());
                    operations.setGenre(test.get(bou.getIconTextGap()).getGenre());
                    operations.setChemin(test.get(bou.getIconTextGap()).getChemin());
                    try {
                        operations.setQualite();
                    } catch (JavaLayerException ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedAudioFileException ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    }

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
                            T1.stop();
                            T2.stop();
                        } else if (s2.isPlaying()) {
                            T1.stop();
                            T2.stop();
                        }
                        s = new Sound(chemin2);
                        operations.ajouterBDD();
//                        threadLecture = new Thread(new PlaySoundBouton(s));
//                        threadLecture.start();
                        s.setIsPlaying(true);
                        T1 = new Thread(new ThreadDemo("Thread-1", s));
                        T1.start();

                        T2 = new Thread(new ThreadDemo2("Thread-2", barreMusique, s));
                        T2.start();
                        actualiserInformations();

                    } catch (Exception ex) {
                        Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    actualiserImage();

                    actualiserInformations();
                }
            });

            bou.setBorderPainted(false);
            maBibli.add(bou);
            JLabel l2 = new JLabel(test.get(bou.getIconTextGap()).getAuteur());
            JButton bou2 = new JButton();
            bou2.setBorderPainted(false);
            l2.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
            bou2.add(l2);
            maBibli.add(bou2);

            long duration = test.get(bou.getIconTextGap()).getDuree();
            JLabel l3 = new JLabel((String.format("%02d:%02d", TimeUnit.MICROSECONDS.toMinutes(duration) - (TimeUnit.MICROSECONDS.toHours(duration) * 60),
                    TimeUnit.MICROSECONDS.toSeconds(duration) - (TimeUnit.MICROSECONDS.toMinutes(duration) * 60))));
            JButton bou3 = new JButton();
            l3.setHorizontalTextPosition((int) LEFT_ALIGNMENT);
            bou3.setBorderPainted(false);
            bou3.add(l3);
            maBibli.add(bou3);

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

        tempsRestant.setText(barreMusique.getValue() + "");
        tempsTotal.setText(operations.getDuree());
        actualiserPanelCoteEst(titre, auteur, duree, album, annee, genre, qualite, new Font("Times New Roman", Font.PLAIN, 16), new Font("Times New Roman", Font.BOLD, 24));
        this.monPanel.revalidate();
        System.out.println("mis a jour du panel");

    }

    private void actualiserStats(Statistiques s) {
        maStats.removeAll();

        try {
            stats.recupererGenre();
//             actualiserInformations();
        } catch (SQLException ex) {
            Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
        }

        test2 = stats.label();
        for (jstats = 0; jstats < test2.size(); jstats++) {

            final JButton bou2 = new JButton(test2.get(jstats));
            bou2.setIconTextGap(jstats);
            bou2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    String genre = (test2.get(bou2.getIconTextGap()));
                    System.out.println(genre);

                    statsAfiiche = operations.statsNbEcoute(genre);
                    statsGraphe s = new statsGraphe("Stat", "Les stats de " + genre, statsAfiiche);
                    s.pack();
                    s.setVisible(true);

                    actualiserInformations();

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

            FloatControl gainControl = (FloatControl) line.getControl(FloatControl.Type.VOLUME);

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

        if (operations.getPicture() == null) {

            img.setIcon(new ImageIcon("Design/Boutons/imgSon.png"));

        } else {

            Image resizedImage = operations.getPicture().getScaledInstance(200, 200, 0);

            img.setIcon(new ImageIcon(resizedImage));

        }

    }

    public class ThreadDemo extends Thread {

        private Thread t;
        private String threadName;
        private Sound s;

        public ThreadDemo(String name, Sound s) {
            threadName = name;
            this.s = s;
            System.out.println("Creating " + threadName);
        }

        public void run() {
            try {

                lireMusique();
                actualiserInformations();

            } catch (Exception ex) {
                Logger.getLogger(ThreadDemo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        public void lireMusique() throws Exception {
            this.s.player.play();
            actualiserInformations();
        }

        public void start() {
            System.out.println("Starting " + threadName);
            if (t == null) {
                t = new Thread(this, threadName);
                t.start();
            }
        }

    }

    public class ThreadDemo2 extends Thread {

        private Thread t;
        private String threadName;
        public long chrono = 0;
        public Thread threadBarre;
        public JSlider barreMu;
        public Sound s;

        public ThreadDemo2(String name, JSlider barreMusique, Sound sound) {
            threadName = name;
            this.s = sound;
            this.barreMu = barreMusique;
            System.out.println("Creating " + threadName);
        }

        public void arret() {
            t.destroy();
        }

        public void run() {
            barreMu.setValue(0);
            Go_Chrono();
            bougerBarreMusique();
            actualiserInformations();
        }

        public void start() {
            System.out.println("Starting " + threadName);
            if (t == null) {
                t = new Thread(this, threadName);
                t.start();
            }
        }

        public void bougerBarreMusique() {
            try {
                System.out.println("Je rentre dans la fonction bougerBarreMusique");
                while (this.barreMu.getValue() != 100) {
                    this.barreMu.setValue(getPourcent());
                    System.out.println("LA BARRE DE MUSIQUE BOUGE");
                    threadBarre.sleep(1000);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public int getPourcent() {
            return Sec(Stop_Chrono()) / s.Pourcent();
        }

        public long Stop_Chrono() {
            long chrono2 = java.lang.System.currentTimeMillis();
            long temps = chrono2 - chrono;
            return temps;

        }

        public int Sec(long temps) {
            long totalSeconds = temps / 1000;
            int second = (int) (totalSeconds % 60);
            long totalMinutes = totalSeconds / 60;
            int minute = (int) (totalMinutes % 60);
            long totalHours = totalMinutes / 60;
            int hour = (int) (totalHours % 24);
            return (hour * 3600) + (minute * 60) + second;
        }

        public void Go_Chrono() {
            chrono = 0;
            chrono = java.lang.System.currentTimeMillis();
        }

    }
}
