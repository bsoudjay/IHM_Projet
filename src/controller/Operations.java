/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import BDD.DOA;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
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
    private String annee;
    private String genre;
    private String qualite;
    private String tempsRestant;
    private Connection con;
    private int volume;
    private DOA doa;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public Operations() throws SQLException {
        this.doa = new DOA("jdbc:mysql://localhost:8889/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "root");
        if (this.doa.connexion()) {
            this.con = DriverManager.getConnection(doa.getURL(), doa.getUser(), doa.getPassword());
        }
    }

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
            album = sound.getAlbum();
            duree = sound.getDuree().toString();
            titre = sound.getTitre();
            annee = sound.getAnnee();
            genre = sound.getGenre();
            qualite = sound.getQualite().toString();
            tempsRestant = sound.calculerTempsRestant() + "";
            System.out.println("L'auteur est " + sound.getAuteur() + " et le titre est " + sound.getTitre());

        }
        doa.connexion();
        this.ajouterBDD();
        
    }

    public boolean verifier() {
        String query = "SELECT * FROM musique WHERE nom = '" + this.getTitre() + "'";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String nom = result.getString(1);
                if (nom != "") {
                    return true;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public void ajouterBDD() {
        String query = null;
        if (!this.verifier()) {
            query = "INSERT INTO musique (nom,artiste,album,duree,nbecoute) VALUES ('" + this.getTitre() + "','" + this.getAuteur() + "','" + this.getAlbum() + "'," + this.duree + ",'" + 1 + "')";
            System.out.println("insertion");
        } else {
            query = "UPDATE musique SET nbecoute = " + (this.nbEcoute()+1) + " WHERE nom = '" + this.getTitre() + "'";  
            System.out.println("mise à jour");
        }
        System.out.println("etape 1");
        try {
            System.out.println("etape 2r");
            Statement requete = con.createStatement();
            System.out.println("etape 2rprme");
            requete.executeUpdate(query);
            System.out.println("etape 3r");
        } catch (Exception e1) {
            System.out.println("etape 2m");
            e1.printStackTrace();
        }
    }

    public int nbEcoute() {
        String query = "SELECT nbecoute FROM musique WHERE nom = '" + this.getTitre() +"'" ;
         try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                int i = result.getInt("nbecoute");
                System.out.println("incrementation");
                return i;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
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
            return "";
        }
    }

    public String getDuree() {
        if (sound != null) {
            long duree = sound.getDuree();
            return String.format("%02d:%02d:%02d",
                    TimeUnit.MICROSECONDS.toHours(duree) - ((int) TimeUnit.MICROSECONDS.toDays(duree) * 24),
                    TimeUnit.MICROSECONDS.toMinutes(duree) - (TimeUnit.MICROSECONDS.toHours(duree) * 60),
                    TimeUnit.MICROSECONDS.toSeconds(duree) - (TimeUnit.MICROSECONDS.toMinutes(duree) * 60));
        } else {
            return "";
        }
    }

    public String getAlbum() {
        //notifierObs();
        if (sound != null) {
            return sound.getAlbum();
        } else {
            return "";
        }
    }

    public String getTitre() {
        if (sound != null) {
            
           if(sound.getTitre().isEmpty()){
               this.setTitre("Sans Titre");
               return "Sans Titre";
           }
           
            return sound.getTitre();
        } else {
            this.setTitre("Sans Titre");
            return "Sans Titre";
        }
    }

    public String getAnnee() {
        if (sound != null) {
            return sound.getAnnee();
        } else {
            return "";
        }
    }

    public String getGenre() {
        if (sound != null) {
            return sound.getGenre();
        } else {
            return "";
        }
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getQualite() {
        if (sound != null) {
            return sound.getQualite() + " Kbps/s";
        } else {
            return "";
        }
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public String getTempsRestant() {
        return tempsRestant;
    }

    public void setTempsRestant(String tempsRestant) {
        this.tempsRestant = tempsRestant;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
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
    
    public String calculTempsRestant() {
        long duree = sound.getTempsRestant();
        return String.format("%02d:%02d:%02d",
                    TimeUnit.MICROSECONDS.toHours(duree) - ((int) TimeUnit.MICROSECONDS.toDays(duree) * 24),
                    TimeUnit.MICROSECONDS.toMinutes(duree) - (TimeUnit.MICROSECONDS.toHours(duree) * 60),
                    TimeUnit.MICROSECONDS.toSeconds(duree) - (TimeUnit.MICROSECONDS.toMinutes(duree) * 60));
    }

    public void lire() {

        if (sound != null) {
            try {

                if (sound.isPlaying() == false) {
                    sound.play();
                } else {
                    System.out.println("stop");
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
