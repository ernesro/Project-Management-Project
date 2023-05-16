package com.example.gestionproyectos.data;

import com.example.gestionproyectos.clases.Project;
import com.example.gestionproyectos.controllers.ProjectController;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Class that connects to the database
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class dataBase {
    public static Connection con = null;
    public static PreparedStatement pst = null;
    /**
     * Method that connects to the database
     * @return True if the connection is successful, false if not
     */
    public static boolean connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/proyectos","root","");
        } catch (Exception e){
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;
    }

    /**
     * Method that closes the connection to the database
     */
    public static void close()
    {
        if(con != null) {
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
