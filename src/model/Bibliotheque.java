/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import controller.Operations;
import java.sql.SQLException;

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

    public void recupererMusique() throws SQLException, Exception {
        this.bibliotheque.clear();
        Operations op = new Operations();
        this.bibliotheque=op.bibliotheque();
        this.notifierObservateursNouveauVolume();
    }
    
    public void recupererMusiqueComplete() throws SQLException, Exception {
        this.bibliotheque.clear();
        Operations op = new Operations();
        ArrayList<String> biblio =op.bibliothequeComplete();
        this.notifierObservateursNouveauVolume();
    }


    public int size() {

        return this.bibliotheque.size();
    }

    public ArrayList<Musique> label() {

      

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
