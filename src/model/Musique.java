/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;

/**
 *
 * @author utilisateur
 */
public class Musique {
    
    private String album;
    private String duree;
    private String titre;
    private String annee;
    private String genre;
    private String auteur;
    private File chemin;
    private int nbEcoute;

    public Musique(String titre, String auteur, String album, String duree, int nbEcoute, String genre, File chemin) {
        this.album = album;
        this.duree = duree;
        this.titre = titre;
        this.annee = annee;
        this.genre = genre;
        this.auteur = auteur;
        this.chemin = chemin;
        this.nbEcoute = nbEcoute;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuree() {
   
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public File getChemin() {
        return chemin;
    }

    public void setChemin(File chemin) {
        this.chemin = chemin;
    }

    public int getNbEcoute() {
        return nbEcoute;
    }

    public void setNbEcoute(int nbEcoute) {
        this.nbEcoute = nbEcoute;
    }
    
    
    
    
    
    
}
