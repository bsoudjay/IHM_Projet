/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JLabel;

/**
 *
 * @author utilisateur
 */
public class Bibliotheque {

    private ArrayList<Musique> bibliotheque;
    private String nom;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public Bibliotheque(String nom) {

        this.observateurs = new ArrayList<Observateur>();
        this.nom = nom;
        this.bibliotheque = new ArrayList<Musique>();

    }

    public void ajouterMusique(Musique m) {

        this.bibliotheque.add(m);
        this.notifierObservateursNouveauVolume();
    }

    public void trierBibliothequeOrdreAlpha() {

        Collections.sort(this.bibliotheque);

    }

    public int size() {

        return this.bibliotheque.size();
    }

    public ArrayList<JLabel> label() {

        trierBibliothequeOrdreAlpha();


        System.out.println(bibliotheque.size());

        ArrayList<JLabel> labels = new ArrayList();

        for (int i = 0; i < this.bibliotheque.size(); i++) {

            JLabel test = new JLabel();
            test.setText("" + this.bibliotheque.get(i).getTitre());
            labels.add(test);

        }


        return labels;

    }

    public void ajouterObservateur(Observateur o) {
        this.observateurs.add(o);
    }

    public void supprimerObservateur(Observateur o) {
        this.observateurs.remove(o);
    }

    public void notifierObservateursNouveauVolume() {
        for (int i = 0; i < this.observateurs.size(); i++) {
            this.observateurs.get(i).actualiserInformations();
        }
    }
}
