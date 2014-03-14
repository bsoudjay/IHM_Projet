<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kevin
 */
public class actionDOA {

    private DOA doa;
    private Connection con;

    public actionDOA() throws SQLException {
        this.doa = new DOA("jdbc:mysql://localhost:3306/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "");
        if (this.doa.connexion()) {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "");
        }
    }

    public boolean Connection() throws SQLException {
        try {
            if (this.doa.connexion()) {
                this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("m");
            ex.printStackTrace();
        }
        return false;
    }

    
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author kevin
 */
public class actionDOA {

    private DOA doa;
    private Connection con;

    public actionDOA() throws SQLException {
        this.doa = new DOA("jdbc:mysql://localhost:3306/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "");
        if (this.doa.connexion()) {
            this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "");
        }
    }

    public boolean Connection() throws SQLException {
        try {
            if (this.doa.connexion()) {
                this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdd_ihm?zeroDateTimeBehavior=convertToNull", "root", "");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("m");
            ex.printStackTrace();
        }
        return false;
    }

    
}
>>>>>>> branch 'master' of https://github.com/bsoudjay/IHM_Projet.git
