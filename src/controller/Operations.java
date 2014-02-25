/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.*;

/**
 *
 * @author bahia
 */
public class Operations {

    private Musique musique;
    private String album;
    private String duree;
    private String titre;
    private int volume;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public void ouvrirFenetre() {
        this.observateurs = new ArrayList<Observateur>();
        this.volume = 0;
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Ajouter une musique");
        fc.setApproveButtonText("Ajouter une musique");
        fc.setFileFilter(new FileNameExtensionFilter("Fichier MP3 (.mp3)", "mp3"));
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fichierMP3 = fc.getSelectedFile();
            musique = new Musique(fichierMP3);
            album = musique.getAlbum();
            duree = musique.getDuree();
            titre = musique.getTitre();
            System.out.println("L'auteur est " + musique.getAuteur() + " et le titre est " + musique.getTitre());
        }
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
        if (musique != null) {
            return musique.getAuteur();
        } else {
            return "inconnu";
        }
    }

    public String getDuree() {
        if (musique != null) {
            return musique.getDuree();
        } else {
            return "inconnu";
        }
    }

    public String getAlbum() {
        //notifierObs();
        if (musique != null) {
            return musique.getAlbum();
        } else {
            return "inconnu";
        }
    }

    public String getTitre() {
        if (musique != null) {
            return musique.getTitre();
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
