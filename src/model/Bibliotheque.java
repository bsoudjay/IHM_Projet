/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author utilisateur
 */
public class Bibliotheque{
    
    private ArrayList<Musique> bibliotheque;
    private String nom;
    
    public Bibliotheque(String nom){
        
        this.nom=nom;
        this.bibliotheque=new ArrayList<Musique>();
        
    }
    
    public void ajouterMusique(Musique m){
        
        this.bibliotheque.add(m);
    }
    
    public void trierBibliothequeOrdreAlpha(){
        
        Collections.sort(this.bibliotheque);
        
    }
    
    @Override
    public String toString(){
        
        trierBibliothequeOrdreAlpha();
        String test="";
        
        System.out.println(bibliotheque.size());
        
        
        for(int i=0;i<this.bibliotheque.size();i++){
            
            test=test+this.bibliotheque.get(i).getTitre()+"\n";
            System.out.println(this.bibliotheque.get(i).getTitre());
        }
        
        
        
        return test;
        
    }

   
    
    
}
