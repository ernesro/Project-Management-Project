package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.data.dataBase;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dataBase.connect()) {

        }
            else //createErrorAlert("MySql Connection");
    }
}
