/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.Sound;
import BDD.DOA;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javazoom.jl.decoder.JavaLayerException;
import model.*;
import vue.DesignMP3;

/**
 *
 * @author bahia
 */
public class Operations {

    private Sound sound;
    private int musiqueLancee = 0;
    //private Musique musique;
    private String tempsRestant;
    private Connection con;
    private int volume;
    private DOA doa;
    private ArrayList<Observateur> observateurs = new ArrayList<Observateur>();

    public Operations() throws SQLException, Exception {

        // Ilan  
        //this.doa = new DOA("jdbc:mysql://localhost:8889/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "root");
        //kevin
        this.doa = new DOA("jdbc:mysql://localhost:3306/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "");
        if (this.doa.connexion()) {
            this.con = DriverManager.getConnection(doa.getURL(), doa.getUser(), doa.getPassword());
        }
        sound = new Sound();
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
            System.out.println("lien " + fichierMP3);
            sound = new Sound(fichierMP3.getPath());
            tempsRestant = sound.calculerTempsRestant() + "";
            System.out.println("L'auteur est " + sound.getAuteur() + " et le titre est " + sound.getTitre());

        }

        doa.connexion();
        this.ajouterBDD();

    }

    public ArrayList<Musique> recherche(String contenu) {
        ArrayList<Musique> maRecherche = new ArrayList<Musique>();
        String query = "SELECT * FROM musique WHERE titre LIKE '%" + contenu + "%' OR auteur = '%" + contenu + "%'";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String titre2 = result.getString(1);
                String auteur2 = result.getString(2);
                String album2 = result.getString(3);
                Long duree2 = result.getLong(4);
                int nbEcoute2 = result.getInt(5);
                String genre2 = result.getString(6);
                String chemin2 = result.getString(7);
                maRecherche.add(new Musique(titre2, auteur2, album2, duree2, nbEcoute2, genre2, new File(chemin2)));
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return maRecherche;
    }

    public int getMusiqueLancee() {
        return this.musiqueLancee;
    }

    public void setMusiqueLancee(int i) {
        this.musiqueLancee = i;
    }

    public boolean verifier() {
        String query = "SELECT * FROM musique WHERE titre = '" + this.getTitre() + "'";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String titre = result.getString(1);
                if (titre != "") {
                    return true;
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return false;
    }

    public void ajouterBDD() throws FileNotFoundException {
        String query = null;
        String chemin_tmp = this.getChemin().toString();
        chemin_tmp = chemin_tmp.replace("\\", "\\\\");
        if (!this.verifier()) {

            query = "INSERT INTO musique (titre,auteur,album,duree,genre,chemin) VALUES ('" + this.getTitre() + "','" + this.getAuteur() + "','" + this.getAlbum() + "'," + sound.getDuree() + ",'" + this.getGenre() + "','" + chemin_tmp + "')";

            System.out.println("insertion");
        } else {
            query = "UPDATE musique SET nbecoute = " + (this.nbEcoute() + 1) + " WHERE titre = '" + this.getTitre() + "'";
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

    public String reecouterMusic(String titre) {
        String query = null;
        query = "SELECT chemin FROM musique WHERE titre = '" + titre + "'";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            System.out.println("test");
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String chemin = result.getString(1);
                System.out.println(chemin);
                return chemin;
            }
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        return null;
    }

    public ArrayList<Musique> bibliotheque() {
        String query = null;
        query = "SELECT titre,auteur,album,duree,nbEcoute,genre,chemin FROM musique";
        ArrayList<Musique> biblio = new ArrayList<Musique>();
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String titre2 = result.getString(1);
                String auteur2 = result.getString(2);
                String album2 = result.getString(3);
                Long duree2 = result.getLong(4);
                int nbEcoute2 = result.getInt(5);
                String genre2 = result.getString(6);
                String chemin2 = result.getString(7);

                biblio.add(new Musique(titre2, auteur2, album2, duree2, nbEcoute2, genre2, new File(chemin2)));

            }
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        return biblio;
    }

    public ArrayList<String> bibliothequeComplete() {
        String query = null;
        query = "SELECT titre, auteur, duree FROM musique";
        ArrayList<String> biblio = new ArrayList<String>();
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String title = result.getString(1);
                String author = result.getString(2);
                String duration = result.getString(3);
                biblio.add(title);
                biblio.add(author);
                biblio.add(duration);
            }
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        return biblio;
    }

    public int nbEcoute() {
        String query = "SELECT nbecoute FROM musique WHERE titre = '" + this.getTitre() + "'";
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

    public void augmenterSon() throws LineUnavailableException {
        sound.augmenterSon();
    }

    public void diminuerSon() throws LineUnavailableException {
        sound.diminuerSon();
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

    public File getChemin() {
        if (sound != null) {
            return sound.getChemin();
        } else {
            return null;
        }
    }

    public String getTitre() {
        if (sound != null) {

            if (sound.getTitre().isEmpty()) {
                sound.setTitre("Sans Titre");
                return "Sans Titre";
            }

            return sound.getTitre();
        } else {
            sound.setTitre("Sans Titre");
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
        if(sound.getGenre()==null){
            return " ";
        }else{
        return sound.getGenre();
        }
    }

    public void setGenre(String genre) {
        sound.setGenre(genre);
    }

    public String getQualite() {
        if (sound != null) {
            return sound.getQualite() + " Kbps/s";
        } else {
            return "";
        }
    }

    public void setChemin(File chemin) {

        sound.setChemin(chemin);

    }

    public void setQualite() throws JavaLayerException, UnsupportedAudioFileException, IOException {
        sound.setQualite();
    }

    public String getTempsRestant() {
        return tempsRestant;
    }

    public void setTempsRestant(String tempsRestant) {
        tempsRestant = tempsRestant;
    }

    public void setAnnee(String annee) {
        sound.setAnnee(annee);
    }

    public void setAlbum(String album) {
        sound.setAlbum(album);
    }

    public void setAuteur(String auteur) {
        sound.setAuteur(auteur);
    }

    public void setDuree(long duree) {
        try {
            sound.setDuree(duree);
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
    }

    public void setTitre(String titre) {
        sound.setTitre(titre);
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
                    musiqueLancee = 1;
                    sound.play();
                } else {
                    System.out.println("stop");
                    musiqueLancee = 2;
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

    public ArrayList<String> bibliothequeGenre() {
        String query = null;
        query = "SELECT genre FROM musique";
        ArrayList<String> biblio = new ArrayList<String>();
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String genre = result.getString(1);
                if (!biblio.contains(genre)) {
                    biblio.add(genre);
                }

            }
        } catch (Exception e1) {
            System.out.println("etape 2m");
            e1.printStackTrace();
        }
        return biblio;
    }

    public ArrayList<Musique> statsNbEcoute(String genre) {
        String query = null;
        query = "SELECT titre,auteur,nbEcoute FROM musique WHERE genre = '" + genre + "'";
        ArrayList<Musique> biblio = new ArrayList<Musique>();
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String titre2 = result.getString(1);
                String auteur2 = result.getString(2);
                int nbEcoute2 = result.getInt(3);

                biblio.add(new Musique(titre2, auteur2, nbEcoute2));

            }
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        return biblio;
    }

    public String Diapo() {
        String chemin = null;
        Random r = new Random();
        int valeur = 1 + r.nextInt(recupDernierId());
        String query = "SELECT url FROM image WHERE id_image = '" + valeur + "'";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                chemin = result.getString(1);
                return chemin;
            }
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        return null;
    }

    public int recupDernierId() {
        String query = "SELECT MAX(id_image) FROM image";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                int id = result.getInt(1);
                return id;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return 0;
    }
    
           public BufferedImage getPicture(){
           if(sound.getImage()==null){
               System.out.println("pas dimage");
           }
            return sound.getImage();
    }
     
    public void setImage(File chemin) throws IOException, UnsupportedTagException, InvalidDataException{
         
        sound.setImage(sound.chargementImage(chemin));
    }
    
    public int recupId(String chemin_tmp){
        String query = null;
        chemin_tmp = this.getChemin().toString();
        chemin_tmp = chemin_tmp.replace("\\", "\\\\");
        System.out.println("wwwwwwwwwwwww  "+chemin_tmp);
        query = "SELECT id_musique FROM musique WHERE chemin = '"+ chemin_tmp +"'";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            System.out.println("test");
            ResultSet result = requete.executeQuery(query);
            while (result.next()){
                System.out.println("aeaee1");
                int id = result.getInt(1);
                System.out.println("aeaeaeae2   "+id);
                return id;
            }
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        System.out.println("azzazazazazazazazazazazazazzazazazazzazaza");
        return 0;
    }
    
    public String recupChemin(int id){
        String query = null;
        query = "SELECT chemin FROM musique WHERE id_musique = " + id + "";
        try {
            Statement requete = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet result = requete.executeQuery(query);
            while (result.next()) {
                String chemin = result.getString(1);
                return chemin;
            }
        } catch (Exception e1) {

            e1.printStackTrace();
        }
        return null;
    }
}
