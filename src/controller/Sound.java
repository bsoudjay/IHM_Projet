package controller;

import javazoom.jl.player.advanced.*;
import java.io.*;

        
        // MP3, WMA, MPEG, WAV compatible
        
        public class Sound {
            private Thread playerThread;	
               

            //constructeur
                public Sound(String path) throws Exception {
                        InputStream in = (InputStream)new BufferedInputStream(new FileInputStream(new File(path)));
                        player = new AdvancedPlayer(in);
                }
                
                public Sound(String path,PlaybackListener listener) throws Exception {
                        InputStream in = (InputStream)new BufferedInputStream(new FileInputStream(new File(path)));
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
                
                public void play(int begin,int end) throws Exception {
                        if (player != null) {
                                isPlaying = true;
                                player.play(begin,end);
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

                private boolean isPlaying = false;
                private AdvancedPlayer player = null;
        }