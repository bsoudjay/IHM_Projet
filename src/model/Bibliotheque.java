/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author utilisateur
 */
public class Bibliotheque {

    private ArrayList<Sound> bibliotheque;
    private String nom;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public Bibliotheque(String nom) {

        this.observateurs = new ArrayList<Observateur>();
        this.nom = nom;
        this.bibliotheque = new ArrayList<Sound>();

    }

    public void ajouterMusique(Sound m) {

        this.bibliotheque.add(m);
        this.notifierObservateursNouveauVolume();
    }

    public void trierBibliothequeOrdreAlpha() {

        Collections.sort(this.bibliotheque);

    }

    public int size() {

        return this.bibliotheque.size();
    }

    public ArrayList<Sound> label() {

        trierBibliothequeOrdreAlpha();

        return this.bibliotheque;

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
