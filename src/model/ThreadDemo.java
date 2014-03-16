/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author kevin
 */
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class ThreadDemo extends Thread {
   private Thread t;
   private String threadName;
   private Sound s;
   
   public ThreadDemo( String name,Sound s){
       threadName = name;
       this.s=s;
       System.out.println("Creating " +  threadName );
   }
   public void run() {
       try {
           s.play();
       } catch (Exception ex) {
           Logger.getLogger(ThreadDemo.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public void start ()
   {
      System.out.println("Starting " +  threadName );
      if (t == null)
      {
         t = new Thread (this, threadName);
         t.start ();
      }
   }

}




