/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.rmi.server.Operation;
import java.util.ArrayList;
import java.util.Collections;
import controller.Operations;
import java.sql.SQLException;

/**
 *
 * @author utilisateur
 */
public class Bibliotheque {

    private ArrayList<String> bibliotheque;
    private String nom;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public Bibliotheque(String nom) {

        this.observateurs = new ArrayList<Observateur>();
        this.nom = nom;
        this.bibliotheque = new ArrayList<String>();

    }

    public void recupererMusique() throws SQLException {
        Operations op = new Operations();
        this.bibliotheque=op.bibliotheque();
        this.notifierObservateursNouveauVolume();
    }

    public void trierBibliothequeOrdreAlpha() {

        Collections.sort(this.bibliotheque);

    }

    public int size() {

        return this.bibliotheque.size();
    }

    public ArrayList<String> label() {

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
