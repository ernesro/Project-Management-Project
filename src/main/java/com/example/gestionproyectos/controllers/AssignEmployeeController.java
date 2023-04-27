package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.AssignEmployee;
import com.example.gestionproyectos.clases.CustomAlert;
import com.example.gestionproyectos.clases.Employee;
import com.example.gestionproyectos.clases.Team;
import com.example.gestionproyectos.data.dataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.gestionproyectos.data.dataBase.*;

public class AssignEmployeeController implements Initializable {
    private final String className = "Employee Assign";
    private Team team;
    private Employee employee;

    @FXML
    private TextField employeeTb;
    @FXML
    private TextField teamTb;

    @FXML
    private TableView employeesTv;
    @FXML
    private TableColumn<Employee, String> employeeDniColumn;
    @FXML
    private TableColumn<Employee, String> employeeNameColumn;
    @FXML
    private TableColumn<Employee, String> employeeLNameColumn;
    @FXML
    private TableColumn<Employee, String> employeeEmailColumn;

    @FXML
    private TableView teamsTv;
    @FXML
    private TableColumn<Team, String> teamCodeColumn;
    @FXML
    private TableColumn<Team, String> teamNameColumn;

    @FXML
    private TableView assignEmployeeTv;
    @FXML
    private TableColumn<AssignEmployee, String> assignEmployeeColumn;
    @FXML
    private TableColumn<AssignEmployee, String> assignTeamColumn;

    /*------------LOAD DATA-------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dataBase.connect()){
            refreshEmployees("SELECT * FROM employees");
            ObservableList<Employee> employees = employeesTv.getItems();
            employee = employees.get(0);

            refreshTeams("SELECT * FROM teams");
            ObservableList<Team> teams = teamsTv.getItems();
            team = teams.get(0);

            refreshAssignEmployee("SELECT * FROM assignemployee");
        }
        else{ CustomAlert.createErrorAlert("MySql Connection", className); }
    }

    public void refreshEmployees(String sql){
        String type = "SEARCH";
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        try{
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Employee employee = new Employee();
                employee.setDni(rs.getString("dni"));
                employee.setName(rs.getString("name"));
                employee.setLastName(rs.getString("lastName"));
                employee.setEmail(rs.getString("email"));
                employees.add(employee);
           }
            employeesTv.setItems(employees);
            employeeDniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
            employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            employeeLNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
            employeeEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        }catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void refreshTeams(String sql){
        String type = "SEARCH";
        ObservableList<Team> teams = FXCollections.observableArrayList();
        try{
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Team team = new Team();
                team.setCode(rs.getString("cod"));
                team.setName(rs.getString("name"));
                teams.add(team);
            }
            teamsTv.setItems(teams);
            teamCodeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
            teamNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        }catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void refreshAssignEmployee(String sql){
        String type = "SEARCH";
        ObservableList<AssignEmployee> assignEmployees = FXCollections.observableArrayList();
        try{
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                AssignEmployee assignEmployee = new AssignEmployee();
                assignEmployee.setEmployee(rs.getString("dni"));
                assignEmployee.setTeam(rs.getString("cod_team"));
                assignEmployees.add(assignEmployee);
            }
            assignEmployeeTv.setItems(assignEmployees);
            assignEmployeeColumn.setCellValueFactory(cellData -> cellData.getValue().employeeProperty());
            assignTeamColumn.setCellValueFactory(cellData -> cellData.getValue().teamProperty());
        }catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void employeeClick(){
        Employee selectedEmployee = (Employee) employeesTv.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null){
            employee = selectedEmployee;
            loadEmployee();
        }
        employeeTb.setText(employee.getDni());
    }

    private void loadEmployee() {
        employeeTb.setText(employee.getDni());
    }


    public void teamClick() {
        Team selectedTeam = (Team) teamsTv.getSelectionModel().getSelectedItem();
        if(selectedTeam != null){
            team = selectedTeam;
            loadTeam();
        }
        teamTb.setText(team.getCode());
    }

    private void loadTeam() {
        teamTb.setText(team.getCode());
    }

    public void assignEmployeeTeam() {
        AssignEmployee selectedAssignEmployee = (AssignEmployee) assignEmployeeTv.getSelectionModel().getSelectedItem();
        if(selectedAssignEmployee != null){
            employeeTb.setText(selectedAssignEmployee.getEmployee());
            teamTb.setText(selectedAssignEmployee.getTeam());
        }
    }

    /*------------BUTTONS-------------*/

    public void addAssignEmployee(){
        String type = "INSERT";
        String sql = "INSERT INTO assignemployee (dni, cod_team) VALUES ('" + employeeTb.getText() + "', '" + teamTb.getText() + "')";
        try{
            pst = con.prepareStatement(sql);
            int status = pst.executeUpdate();
            if(status > 0){
                CustomAlert.createSuccesAlert(type, className);
                refreshAssignEmployee("SELECT * FROM assignemployee");
            }
            else{ CustomAlert.createErrorAlert(type, className); }
        }catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void searchEmployeeInEmployeesBt() {
        refreshEmployees("SELECT * FROM employees WHERE dni = '" + employeeTb.getText() + "'");
    }

    public void searchTeamInTeams() {
        refreshTeams("SELECT * FROM teams WHERE cod = '" + teamTb.getText() + "'");
    }

    public void clearBt() {
        employeeTb.setText("");
        teamTb.setText("");
    }

    public void deleteBt() {
        String type = "DELETE";
        String sql = "DELETE FROM assignemployee WHERE dni = '" + employeeTb.getText() + "' AND cod_team = '" + teamTb.getText() + "'";
        try{
            pst = con.prepareStatement(sql);
            int status = pst.executeUpdate();
            if(status > 0){
                CustomAlert.createSuccesAlert(type, className);
                refreshAssignEmployee("SELECT * FROM assignemployee");
            }
            else{ CustomAlert.createErrorAlert(type, className); }
        }catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void searchByTeamBt() {
        refreshAssignEmployee("SELECT * FROM assignemployee WHERE cod_team = '" + teamTb.getText() + "'");
    }

    public void searchByEmployeeBt() {
        refreshAssignEmployee("SELECT * FROM assignemployee WHERE dni = '" + employeeTb.getText() + "'");
    }

    public void showAllBt() {
        refreshEmployees("SELECT * FROM employees");
        refreshTeams("SELECT * FROM teams");
        refreshAssignEmployee("SELECT * FROM assignemployee");
    }
}
