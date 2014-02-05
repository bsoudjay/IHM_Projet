package vues;


import java.awt.BorderLayout;
import javax.swing.JFrame;

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
        this.setSize(600,600);
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
        Conteneur conteneurBas = new Conteneur();
        conteneurBas.add(b1);
        conteneurBas.add(b2);
        conteneurBas.add(b3);
        return conteneurBas;    
    }
    
    private Conteneur ajouterConteneurGeneral () {
        
        Conteneur conteneurGeneral = new Conteneur();
        conteneurGeneral.setLayout(new BorderLayout());
        conteneurGeneral.add(ajouterConteneurBas(), BorderLayout.SOUTH);
        return conteneurGeneral;
    }
    
}
