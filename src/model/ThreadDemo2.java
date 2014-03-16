/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JSlider;
import vue.DesignMP3;

/**
 *
 * @author kevin
 */
public class ThreadDemo2 extends Thread {

    private Thread t;
    private String threadName;
    public static long chrono = 0;
    public Thread threadBarre;
    public JSlider barreMu;
    public Sound s;

    public ThreadDemo2(String name, JSlider barreMusique,Sound sound) {
        threadName = name;
        this.s=sound;
        this.barreMu=barreMusique;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        bougerBarreMusique();
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
        public void bougerBarreMusique() {
        try {
            System.out.println("Je rentre dans la fonction bougerBarreMusique");
            while (this.barreMu.getValue() != 100) {
                System.out.println(s.getPourcent());
                this.barreMu.setValue(s.getPourcent());
                System.out.println("LA BARRE DE MUSIQUE BOUGE");
                threadBarre.sleep(1000);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(DesignMP3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static long Stop_Chrono() {
        long chrono2 = java.lang.System.currentTimeMillis();
        long temps =  chrono2 -  chrono;
        return temps;
        
    }
    
    public static int Sec(long temps){
       long totalSeconds=temps/1000;  
        int second=(int)(totalSeconds%60);  
        long totalMinutes=totalSeconds/60;  
        int minute=(int)(totalMinutes%60);  
        long totalHours=totalMinutes/60;  
        int hour=(int)(totalHours%24);
        return (hour * 3600) + (minute * 60) + second;
    }

     public static void Go_Chrono() {
         chrono=0;
        chrono = java.lang.System.currentTimeMillis();
    }


}
