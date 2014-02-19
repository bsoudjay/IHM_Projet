/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Musique;

/**
 *
 * @author bahia
 */
public class Operations {

    private static Musique musique;

    public static void ouvrirFenetre() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Ajouter une musique");
        fc.setApproveButtonText("Ajouter une musique");
        fc.setFileFilter(new FileNameExtensionFilter("Fichier MP3 (.mp3)", "mp3"));
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File fichierMP3 = fc.getSelectedFile();
            musique = new Musique(fichierMP3);
            System.out.println("L'auteur est " + musique.getAuteur() + " et le titre est " + musique.getTitre());
        }
    }
}
