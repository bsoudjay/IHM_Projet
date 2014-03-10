/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.*;
import vue.DesignMP3;

/**
 *
 * @author bahia
 */
public class Operations {

    private Sound sound;
    //private Musique musique;
    private String album;
    private String duree;
    private String titre;
    private int volume;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public void ouvrirFenetre() throws Exception {
        this.observateurs = new ArrayList<Observateur>();
        this.volume = 0;
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Ajouter une musique");
        fc.setApproveButtonText("Ajouter une musique");
        fc.setFileFilter(new FileNameExtensionFilter("Fichier MP3 (.mp3)", "mp3"));
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fichierMP3 = fc.getSelectedFile();
            sound = new Sound(fichierMP3.getPath());
            //musique = new Musique(fichierMP3);
            /*musique.setChemin(fichierMP3);            
             album = musique.getAlbum();
             duree = musique.getDuree();
             titre = musique.getTitre();*/
            album = sound.getAlbum();
            duree = sound.getDuree().toString();
            titre = sound.getTitre();
            System.out.println("L'auteur est " + sound.getAuteur() + " et le titre est " + sound.getTitre());

        }
    }

    public Sound getSound() {

        return this.sound;
    }

    public int getVolume() {


        return this.volume;
    }

    public void setVolume(int volume) {

        this.volume = volume;
    }

    public void augmenterVolume() {
        if (this.volume < 10) {
            this.volume++;
            this.notifierObservateursNouveauVolume();
        }
    }

    public void diminuerVolume() {
        if (this.volume > 0) {
            this.volume--;
            this.notifierObservateursNouveauVolume();
        }
    }

    public void silence() {
        if (this.volume != 0) {
            this.volume = 0;
            this.notifierObservateursNouveauVolume();
        }
    }

    public String getAuteur() {
        if (sound != null) {
            return sound.getAuteur();
        } else {
            return "inconnu";
        }
    }

    public String getDuree() {
        if (sound != null) {
            long duree = sound.getDuree();
            int day = (int) TimeUnit.MICROSECONDS.toDays(duree);
            long hours = TimeUnit.MICROSECONDS.toHours(duree) - (day * 24);
            long minute = TimeUnit.MICROSECONDS.toMinutes(duree) - (TimeUnit.MICROSECONDS.toHours(duree) * 60);
            long second = TimeUnit.MICROSECONDS.toSeconds(duree) - (TimeUnit.MICROSECONDS.toMinutes(duree) * 60);
            return hours + ":" + minute + ":" + second;
        } else {
            return "inconnu";
        }
    }

    public String getAlbum() {
        //notifierObs();
        if (sound != null) {
            return sound.getAlbum();
        } else {
            return "inconnu";
        }
    }

    public String getTitre() {
        if (sound != null) {
            return sound.getTitre();
        } else {
            return "inconnu";
        }
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void lire() {

        if (sound != null) {
            try {

                if (sound.isPlaying() == false) {
                    sound.play();
                } else {
                    sound.stop();
                }
            } catch (Exception ex) {
                Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
