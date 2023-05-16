package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.CustomAlert;
import com.example.gestionproyectos.clases.Employee;
import com.example.gestionproyectos.data.dataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.gestionproyectos.data.dataBase.con;
import static com.example.gestionproyectos.data.dataBase.pst;
/**
 * Class that controls the employee window
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class EmployeeController extends CustomAlert implements Initializable {

    private final String className = "Employee";
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
    private TableColumn<Employee, String> dniColumn;
    @FXML
    private TableColumn <Employee, String> lastNameColumn;
    @FXML
    private TableColumn <Employee, String> nameColumn;
    @FXML
    private TableColumn <Employee, String> phoneColumn;
    @FXML
    private TableColumn <Employee, String> addressColumn;
    @FXML
    private TableColumn <Employee, String> emailColumn;


    /*-------------- LOAD DATA --------------*/
    /**
     * Method that loads the data from the database to the table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (dataBase.connect()) {
            refreshTable("SELECT * FROM employees ORDER BY dni");
            ObservableList<Employee> items = employeesTv.getItems();
            employee = items.get(0);
            loadTb();
        }
        else CustomAlert.createErrorAlert("MySql Connection", className);
    }

    /**
     * Method that loads the text fields with the data from the classes
     */
    public void loadTb(){
        dniTb.setText(employee.getDni());
        lastNameTb.setText(employee.getLastname());
        nameTb.setText(employee.getName());
        phoneTb.setText(employee.getPhone());
        addressTb.setText(employee.getAddress());
        emailTb.setText(employee.getEmail());
    }

    /**
     * Method that loads employee data from the class when the table is clicked
     */
    public void clickOnTableView(){
        Employee selectedItem = (Employee) employeesTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            employee = selectedItem;
            loadTb();
        }
    }

    /**
     * Methodthat refreshes the employees table with the data from the database
     * @param sql
     */
    public void refreshTable(String sql){
        String type = "Search";
        ObservableList<Employee> items = FXCollections.observableArrayList();
        try {
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setDni(rs.getString("dni"));
                employee.setLastName(rs.getString("lastName"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phoneNumber"));
                employee.setAddress(rs.getString("address"));
                employee.setEmail(rs.getString("email"));
                items.add(employee);
            }
            employeesTv.setItems(items);
            dniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
            nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            phoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
            addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
            emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        } catch (Exception e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /*-------------- BUTTONS --------------*/
    /**
     * Method that adds a new employee to the database
     */
    public void addBt_Click() {
        String type = "Insert";
        String sql = "INSERT INTO employees (dni, lastName, name, phoneNumber, address, email) VALUES (?,?,?,?,?,?)";
        try {
            dataBase.pst = con.prepareStatement(sql);
            pst.setString(1, dniTb.getText());
            pst.setString(2, lastNameTb.getText());
            pst.setString(3, nameTb.getText());
            pst.setString(4, phoneTb.getText());
            pst.setString(5, addressTb.getText());
            pst.setString(6, emailTb.getText());
            int status = pst.executeUpdate();
            if(status > 0){
                CustomAlert.createSuccesAlert(type, className);
                refreshTable("SELECT * FROM employees ORDER BY dni");
            }
            else
                CustomAlert.createErrorAlert(type, className);
        } catch (Exception e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method that updates the employee data in the database
     */
    public void editBt_Click() {
        String type = "Update";
        String sql = "UPDATE employees SET lastName = ?, name = ?, phoneNumber = ?, address = ?, email = ? WHERE dni = ?";
        try {
            dataBase.pst = con.prepareStatement(sql);
            pst.setString(1, lastNameTb.getText());
            pst.setString(2, nameTb.getText());
            pst.setString(3, phoneTb.getText());
            pst.setString(4, addressTb.getText());
            pst.setString(5, emailTb.getText());
            pst.setString(6, dniTb.getText());
            int status = pst.executeUpdate();
            if(status > 0){
                CustomAlert.createSuccesAlert(type, className);
                refreshTable("SELECT * FROM employees ORDER BY dni");
            }
            else
                CustomAlert.createErrorAlert(type, className);
        } catch (Exception e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Method that deletes an employee from the database
     */
    public void deleteBt_Click() {
        String type = "Delete";
        String sql = "DELETE FROM employees WHERE dni = ?";
        try {
            dataBase.pst = con.prepareStatement(sql);
            pst.setString(1, dniTb.getText());
            int status = pst.executeUpdate();
            if(status > 0){
                CustomAlert.createSuccesAlert(type, className);
                refreshTable("SELECT * FROM employees ORDER BY dni");
            }
            else
                CustomAlert.createErrorAlert(type, className);
        } catch (Exception e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Method that clears the text fields
     */
    public void clearBt_Click() {
        dniTb.setText("");
        nameTb.setText("");
        lastNameTb.setText("");
        phoneTb.setText("");
        addressTb.setText("");
        emailTb.setText("");
    }
    /**
     * Method that shows all the employees in the database
     */
    public void ShowAllBt_Click() {
        refreshTable("SELECT * FROM employees ORDER BY dni");
    }
    /**
     * Method that searches an employee by dni
     */
    public void SearchByDni_Click() {
        refreshTable("SELECT * FROM employees WHERE dni = '" + dniTb.getText() + "'");
    }
}
