/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kevin
 */

import java.sql.*;
public class testkevin {
        public static void main(String[] args) throws Exception {
            try {  
            Class.forName("com.mysql.jdbc.Driver");
            String connectionUrl = "jdbc:mysql://localhost/mysql?" + 
                                   "user=root&password=";
            Connection con = DriverManager.getConnection(connectionUrl);
                System.out.println("r");
        } catch (SQLException e) {
                System.out.println("m1");
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
                System.out.println("m2");
            System.out.println("Class Not Found Exception: "+ cE.toString());
        }
        }
}
