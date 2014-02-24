/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controller.Operations;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.image.ImageProducer;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Observateur;
import vue.DesignMP3;
/**
 *
 * @author ilanmalka
 */
public class Fenetre extends JFrame{
    
    DesignMP3 leDesign = new DesignMP3();
    
    public Fenetre(String text){
        
   
        
        this.setTitle(text);
        this.setSize(1150,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        this.getContentPane().add(leDesign.initialisation());
       
        this.setVisible(true);  
        
    }    

 
}
