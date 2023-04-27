package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.CustomAlert;
import com.example.gestionproyectos.clases.Project;
import com.example.gestionproyectos.data.dataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.gestionproyectos.data.dataBase.con;
import static com.example.gestionproyectos.data.dataBase.pst;

public class ProjectController extends CustomAlert implements Initializable
{
    private final String className = "Project";
    Project actProject;

    @FXML
    private TextArea descTb;
    @FXML
    private TextField codeTb;
    @FXML
    private TextField tltTb;
    @FXML
    private Label stateLb;

    @FXML
    private TableView projTb;
    @FXML
    private TableColumn <Project, String> codeColum;
    @FXML
    private TableColumn <Project, String> titleColum;
    @FXML
    private TableColumn <Project, String> descriptionColum;
    @FXML
    private TableColumn <Project, String> stateColum;

    /*-------------- LOAD DATA --------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descTb.setWrapText(true);
        if (dataBase.connect()) {
            refreshTable("SELECT * FROM proyects ORDER BY cod");
            ObservableList<Project> items = projTb.getItems();
            actProject = items.get(0);
            loadTb();
        }
        else CustomAlert.createErrorAlert("MySql Connection", className);
    }

    public void loadTbFromSelectedTableView() {
        Project selectedItem = (Project) projTb.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actProject = selectedItem;
            loadTb();
        }
    }

    public void loadTb() {
        codeTb.setText(actProject.getCode());
        descTb.setText(actProject.getDescription());
        tltTb.setText(actProject.getTitle());
        stateLb.setText(actProject.getState());
    }

    public void refreshTable(String sql){
        String type = "SEARCH";
        ObservableList<Project> projects = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    Project project = new Project();
                    project.setCode(rs.getString("cod"));
                    project.setTitle(rs.getString("title"));
                    project.setDescription(rs.getString("description"));
                    project.setState(rs.getString("state"));
                    projects.add(project);
                }

                projTb.setItems(projects);
                codeColum.setCellValueFactory(f -> f.getValue().codeProperty());
                titleColum.setCellValueFactory(f -> f.getValue().titleProperty());
                descriptionColum.setCellValueFactory(f -> f.getValue().descriptionProperty());
                stateColum.setCellValueFactory(f -> f.getValue().stateProperty());

        } catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /*-----------  BUTTONS  --------------*/

    public void clearButton() {
        codeTb.setText("");
        descTb.setText("");
        tltTb.setText("");
        stateLb.setText("");
    }

    public void addButton() {
        String type = "INSERT";
        String sql = "INSERT INTO proyects(cod, title, description, state) values (?,?,?,?)";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, codeTb.getText());
            pst.setString(2, tltTb.getText());
            pst.setString(3, descTb.getText());
            pst.setString(4, stateLb.getText());
            int status = pst.executeUpdate();

            if(status == 1){
                CustomAlert.createSuccesAlert(type,className);

                refreshTable("SELECT * FROM proyects ORDER BY cod");
                clearButton();
                codeTb.requestFocus();
            } else {
                CustomAlert.createErrorAlert(type, className);
            }
        } catch (SQLException e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void updateButton() {
        String type = "UPDATE";
        String sql = "UPDATE proyects SET title=?, description=?, state=? WHERE cod=?";
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, tltTb.getText());
            pst.setString(2, descTb.getText());
            pst.setString(3, stateLb.getText());
            pst.setString(4, codeTb.getText());
            int status = pst.executeUpdate();
            if(status == 1){
                CustomAlert.createSuccesAlert(type, className);
                refreshTable("SELECT * FROM proyects ORDER BY cod");
                clearButton();
                codeTb.requestFocus();
            } else {
                CustomAlert.createErrorAlert(type, className);
            }
        } catch (SQLException e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteButton() {
        String type = "DELETE";
        String sql = "DELETE FROM proyects WHERE cod=?";
        String code = codeTb.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            int status = pst.executeUpdate();
            if (status == 1) {
                CustomAlert.createSuccesAlert(type, className);
                refreshTable("SELECT * FROM proyects ORDER BY cod");
                clearButton();
                codeTb.requestFocus();
            } else {
                CustomAlert.createErrorAlert(type, className);
            }
        } catch (SQLException e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void searchByCodeButton() {
        refreshTable("SELECT * FROM proyects WHERE cod='"+ codeTb.getText() +"'");
        ObservableList<Project> items = projTb.getItems();
        if(!items.isEmpty()) {
            actProject = items.get(0);
            loadTb();
        }
    }

    @FXML
    private void allButton() { refreshTable("SELECT * FROM proyects ORDER BY cod"); }

    public void setStateInProgress() {stateLb.setText("in progress");}
    public void setStateOnHold() {stateLb.setText("on hold");}
    public void setStateCompleted() {stateLb.setText("completed");}
}
