/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ilanmalka
 */
public class PanelTout extends JPanel {
    
    private String img;

    
    public PanelTout(String img){
        this.img = img;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        try {
            Image img_fond = ImageIO.read(new File(img));
            g.drawImage(img_fond, 800, 500, this);
            //JPanel PanelSud = panelSud();
            this.revalidate();
            this.repaint();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
