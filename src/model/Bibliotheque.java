/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import controller.Operations;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;

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
    
    public void trierParTitre(){
        Collections.sort(bibliotheque);
    }
    
    public void trierParAuteur(){
        TrierParAuteur tri2 = new TrierParAuteur();
        Collections.sort(bibliotheque,tri2);
    }
    
    public void trierParDuree(){
        TrierParDuree tri3 = new TrierParDuree();
        Collections.sort(bibliotheque,tri3);
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


 class TrierParAuteur implements Comparator{
    
        
    @Override
    public int compare(Object t, Object t1) {
        
        Musique p1 = (Musique)t;
        Musique p2 = (Musique)t1;

        
         return p1.getAuteur().compareTo(p2.getAuteur());
        
}
}
    
 class TrierParDuree implements Comparator{
    
        
    @Override
    public int compare(Object t, Object t1) {
        
        Musique p1 = (Musique)t;
        Musique p2 = (Musique)t1;
        
        if (p1.getDuree() < p2.getDuree()){
            return -1;
        }else if (p1.getDuree() > p2.getDuree()){
            return 1;
        }else{ 
            return 0;
        }
   }
 
}
 



