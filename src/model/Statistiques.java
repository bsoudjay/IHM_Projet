/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

package model;

import controller.Operations;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import model.Observateur;

/**
 *
 * @author kevin
 */
public class Statistiques {
    private ArrayList<String> statistique;
    private String nom;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public Statistiques(String nom) {

       
        this.observateurs = new ArrayList<Observateur>();
        this.nom = nom;
        this.statistique = new ArrayList<String>();

    }

    public void recupererGenre() throws SQLException, Exception {
        this.statistique.clear();
        Operations op = new Operations();
        this.statistique=op.bibliothequeGenre();
        this.notifierObservateursNouveauVolume();
    }

    public void trierBibliothequeOrdreAlpha() {

        Collections.sort(this.statistique);

    }

    public int size() {

        return this.statistique.size();
    }

    public ArrayList<String> label() {

        trierBibliothequeOrdreAlpha();

        return this.statistique;

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
