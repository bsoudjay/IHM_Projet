package model;

import javazoom.jl.player.advanced.*;
import java.io.*;
import java.sql.Connection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Port;
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
    private Integer qualite;
    private String genre;
    private boolean isPlaying = false;
    private AdvancedPlayer player = null;
    private long tempsRestant;

    //constructeur
    public Sound() {
        this.titre = "";
        this.duree = 0L;
        this.album = "";
        this.auteur = "";
        this.qualite = 0;
        this.annee = "";
        this.genre = "";

    }

    public Sound(String path) throws Exception {
        if (path == null) {

            System.out.println("PROBLEMEEEEEE");

        } else {
            chemin = new File(path);
        }
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
            qualite = (Integer) properties.get("mp3.bitrate.nominal.bps") / 1000;
            genre = (String) properties.get("mp3.id3tag.genre");
            tempsRestant = 0;
        } else {
            throw new UnsupportedAudioFileException();
        }




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

    public int getDureeInt() {
        return duree.intValue();
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

    public Integer getQualite() {
        return qualite;
    }

    public void setQualite(Integer qualite) {
        this.qualite = qualite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public long getTempsRestant() {
        return tempsRestant;
    }

    public void setTempsRestant(long tempsRestant) {
        this.tempsRestant = tempsRestant;
    }

    public void diminuerSon() {

        try {
            Line line = AudioSystem.getLine(Port.Info.SPEAKER);
            line.open();

            FloatControl gainControl =
                    (FloatControl) line.getControl(FloatControl.Type.VOLUME);
            System.out.println("Volume avt : " + gainControl.getValue());
             gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
             System.out.println("Volume apres : " + gainControl.getValue());
            //line.
            
            /*
             * Volume mute
             * BooleanControl muteControl = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
             muteControl.setValue(true); */

        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void augmenterSon() {

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(chemin);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.VOLUME);
            gainControl.setValue(gainControl.getValue() + 10.0f); // Reduce volume by 10 decibels.
            clip.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            while (tempsRestant != duree) {
                calculerTempsRestant();
                Thread.sleep(100);
            }
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

    public long calculerTempsRestant() {
        return tempsRestant += System.currentTimeMillis();
    }

    @Override
    public int compareTo(Sound m) {

        return this.album.compareTo(m.getAlbum());

    }
}
