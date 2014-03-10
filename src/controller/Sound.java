package controller;

import javazoom.jl.player.advanced.*;
import java.io.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tritonus.share.sampled.file.TAudioFileFormat;

// MP3, WMA, MPEG, WAV compatible
public class Sound implements Comparable<Sound> {

    private File chemin;
    private Connection con;
    private String titre;
    private String auteur;
    private Long duree;
    private String album;
    private String annee;
    private String qualite;
    private String genre;
    private boolean isPlaying = false;
    private AdvancedPlayer player = null;
    
    //constructeur
    public Sound(String path) throws Exception {
        chemin = new File(path);
        //File file = new File(filename);
        InputStream in = (InputStream) new BufferedInputStream(new FileInputStream(chemin));
        player = new AdvancedPlayer(in);
        
        AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(chemin);
        
    // TAudioFileFormat properties
        if (baseFileFormat instanceof TAudioFileFormat) {
            Map properties = baseFileFormat.properties();
            titre = (String) properties.get("title");
            auteur = (String) properties.get("author");
            duree = (Long) properties.get("duration");
            album = (String) properties.get("album");
            annee = (String) properties.get("date");
            qualite = ((Integer) properties.get("mp3.bitrate.nominal.bps")).toString();
            genre = (String) properties.get("mp3.id3tag.genre");
        } else {
            throw new UnsupportedAudioFileException();
        }

        
    }

    public void ajouterBDD(){
        String query = "INSERT INTO musique (nom,artiste,album,duree,nbecoute) VALUES ('" + this.getTitre() + "','" + this.getAuteur() + "','" + this.getAlbum() + "'," + this.duree + ",'" + this.nbEcoute() + "')";
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
    
        public int nbEcoute(){
        //String query = "SELECT nbecoute FROM musique WHERE nom = ";
        return 0;
    }
    
    public File getChemin() {
        return chemin;
    }

    public void setChemin(File chemin) {
        this.chemin = chemin;
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

    public Long getDuree() {
        return duree;
    }

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getQualite() {
        return qualite;
    }

    public void setQualite(String qualite) {
        this.qualite = qualite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    

    public Sound(String path, PlaybackListener listener) throws Exception {
        InputStream in = (InputStream) new BufferedInputStream(new FileInputStream(new File(path)));
        player = new AdvancedPlayer(in);
        player.setPlayBackListener(listener);
    }

    //methodes
    public void play() throws Exception {
        if (player != null) {
            isPlaying = true;
            player.play();
        }
    }

    public void play(int begin, int end) throws Exception {
        if (player != null) {
            isPlaying = true;
            player.play(begin, end);
        }
    }

    public void stop() throws Exception {
        if (player != null) {
            isPlaying = false;
            player.stop();
        }
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    
    @Override
    public int compareTo(Sound m) {
       
       return this.album.compareTo(m.getAlbum());
        
    }
}
