import java.sql.*;
import BDD.actionDOA;
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevin
 */
public class testKevin2 {


    
    public static void main(String[] args) throws SQLException {
        actionDOA a = new actionDOA();
        System.out.println(a.Connection());
    }
}
