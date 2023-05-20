package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.WindowFXML;
import com.example.gestionproyectos.data.dataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField userTb;
    @FXML
    private TextField passwdTb;
    @FXML
    private Label errorLb;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLb.setVisible(false);
    }

    @FXML
    public void loginBtClick() throws IOException {
        if(dataBase.connect() && checkUser())
        {
            WindowFXML window = new WindowFXML("welcome-view.fxml", 600, 400);
            userTb.setText("");
            passwdTb.setText("");
            errorLb.setVisible(false);
            window.showAndWait();
        }
        dataBase.close();
    }

    private boolean checkUser(){
        String user = userTb.getText();
        String passwd = passwdTb.getText();
        String query = "SELECT * FROM employees WHERE email = '" + user + "' AND dni = '" + passwd + "'";
        try {
            dataBase.pst = dataBase.con.prepareStatement(query);
            ResultSet rs = dataBase.pst.executeQuery();
            if(rs.next()){
                return true;
            }else{
                errorLb.setVisible(true);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
