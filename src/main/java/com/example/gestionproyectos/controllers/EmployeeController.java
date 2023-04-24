package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.ALert;
import com.example.gestionproyectos.clases.Employee;
import com.example.gestionproyectos.clases.Team;
import com.example.gestionproyectos.data.dataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    Employee employee;

    @FXML
    private TextField dniTb;
    @FXML
    private TextField nameTb;
    @FXML
    private TextField lastNameTb;
    @FXML
    private TextField phoneTb;
    @FXML
    private TextField addressTb;
    @FXML
    private TextField emailTb;

    @FXML
    private TableView employeesTv;
    @FXML
    private TableColumn<Employee, String> dniColum;
    @FXML
    private TableColumn <Employee, String> lastNameColum;
    @FXML
    private TableColumn <Employee, String> nameColum;
    @FXML
    private TableColumn <Employee, String> phoneColum;
    @FXML
    private TableColumn <Employee, String> addressColum;
    @FXML
    private TableColumn <Employee, String> emailColum;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dataBase.connect()) {

        }
        else ALert.createErrorAlert("MySql Connection");
    }


}
