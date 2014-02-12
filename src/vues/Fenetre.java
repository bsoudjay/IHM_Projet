package vues;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import model.Musique;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author utilisateur
 */
public class Fenetre extends JFrame {

    public Fenetre(String titre) {
        this.setTitle(titre);
        //taille de la fenêtre
        this.setSize(600, 600);
        //fenêtre visible
        this.setVisible(true);
        //Permet de terminer l'execution du programme en fermant la fenetre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(ajouterConteneurGeneral());
    }

    private Conteneur ajouterConteneurBas() {

        Bouton b1 = new Bouton("<");
        Bouton b2 = new Bouton(">");
        Bouton b3 = new Bouton("||");
        JMenuItem ajouterMusique = new JMenuItem("Ajouter une musique");
        ajouterMusique.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Ajouter une musique");
                fc.setApproveButtonText("Ajouter une musique");
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File fichierMP3 = fc.getSelectedFile();
                    Musique musique = new Musique(fichierMP3);
                    System.out.println("L'auteur est " + musique.getAuteur() + " et le titre est " + musique.getTitre());
                }
            }
        });

        Conteneur conteneurBas = new Conteneur();
        conteneurBas.add(b1);
        conteneurBas.add(b2);
        conteneurBas.add(b3);
        conteneurBas.add(ajouterMusique);
        return conteneurBas;
    }

    private Conteneur ajouterConteneurGeneral() {

        Conteneur conteneurGeneral = new Conteneur();
        conteneurGeneral.setLayout(new BorderLayout());
        conteneurGeneral.add(ajouterConteneurBas(), BorderLayout.SOUTH);
        return conteneurGeneral;
    }
}
