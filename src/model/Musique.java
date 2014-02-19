/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.farng.mp3.*;
import org.farng.mp3.id3.ID3v1;

/**
 *
 * @author Bahia
 */
public class Musique {

    private MP3File fichier;
    private String titre;
    private String auteur;
    private String duree = "à définir" ;
    private String album;

    public Musique(File chemin) {
        try {
           MP3File fichierMP3 = new MP3File(chemin.getPath());
           this.fichier = fichierMP3;
           titre = fichier.getID3v1Tag().getTitle();
           auteur = fichier.getID3v1Tag().getArtist();
           album = fichier.getID3v1Tag().getAlbum();
           //duree = fichier.
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException ex) {
            Logger.getLogger(Musique.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MP3File getFichier() {
        return fichier;
    }

    public void setFichier(MP3File fichier) {
        this.fichier = fichier;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

}
