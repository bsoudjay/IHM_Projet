package controller;

import javazoom.jl.player.advanced.*;
import java.io.*;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tritonus.share.sampled.file.TAudioFileFormat;

// MP3, WMA, MPEG, WAV compatible
public class Sound implements Comparable<Sound> {

    private Thread playerThread;
    private File chemin;
    private String titre;
    private String auteur;
    private Long duree;
    private String album;
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

    public void setDuree(Long duree) {
        this.duree = duree;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
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
            this.playerThread = new Thread((Runnable) this, "AudioPlayerThread");
            this.playerThread.start();
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
            this.playerThread.stop();

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