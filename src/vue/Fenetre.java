/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import controller.Operations;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.image.ImageProducer;
import java.io.File;
import java.rmi.server.Operation;
import java.sql.SQLException;
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
public class Fenetre extends JFrame {
    
    DesignMP3 leDesign = new DesignMP3();
    
    public Fenetre(String text) throws SQLException, Exception{
        
        
        this.setTitle(text);
        this.setSize(1300,700);       
        Operations op=new Operations();
        op.reecouterMusic("Black And Yellow");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
      
        this.getContentPane().add(leDesign.initialisation());
       
        this.setVisible(true); 
        this.setResizable(true);
        
    }    

 
}
