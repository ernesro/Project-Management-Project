package com.example.gestionproyectos.controllers;

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

public class ProjectController implements Initializable
{
    Project Actproject;
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
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        descTb.setWrapText(true);
        dataBase.connect();
        refreshTable("SELECT * FROM proyects ORDER BY cod");
        ObservableList<Project> items = projTb.getItems();
        Actproject = items.get(0);
        loadTb();
        dataBase.close();
    }

    public void loadTbFromSelectedTableView()
    {
        Project selectedItem = (Project) projTb.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            Actproject = selectedItem;
            loadTb();
        }
    }

    public void loadTb()
    {
        codeTb.setText(Actproject.getCode());
        descTb.setText(Actproject.getDescription());
        tltTb.setText(Actproject.getTitle());
        stateLb.setText(Actproject.getState());
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
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    /*-----------  BUTTONS  --------------*/

    public void clearButton()
    {
        codeTb.setText("");
        descTb.setText("");
        tltTb.setText("");
        stateLb.setText("");
    }

    public void addButton()
    {
        String type = "INSERT";
        String sql = "INSERT INTO proyects(cod, title, description, state) values (?,?,?,?)";
        dataBase.connect();
        String code = codeTb.getText();
        String title = tltTb.getText();
        String desc = descTb.getText();
        String state = stateLb.getText();
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            pst.setString(2, title);
            pst.setString(3, desc);
            pst.setString(4, state);
            int status = pst.executeUpdate();

            if(status == 1){
                createSuccesAlert(type);

                refreshTable("SELECT * FROM proyects ORDER BY cod");
                clearButton();
                codeTb.requestFocus();
            } else {
                createErrorAlert(type);
            }
        } catch (SQLException e){
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    public void updateButton()
    {
        String type = "UPDATE";
        String sql = "UPDATE proyects SET title=?, description=?, state=? WHERE cod=?";
        dataBase.connect();

        String code = codeTb.getText();
        String title = tltTb.getText();
        String desc = descTb.getText();
        String state = stateLb.getText();
        try{
            pst = con.prepareStatement(sql);
            pst.setString(4, code);
            pst.setString(1, title);
            pst.setString(2, desc);
            pst.setString(3, state);

            int status = pst.executeUpdate();

            if(status == 1){
                createSuccesAlert(type);

                refreshTable("SELECT * FROM proyects ORDER BY cod");
                clearButton();
                codeTb.requestFocus();
            } else {
                createErrorAlert(type);
            }
        } catch (SQLException e){
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    public void deleteButton() {
        String type = "DELETE";
        String sql = "DELETE FROM proyects WHERE cod=?";
        dataBase.connect();

        String code = codeTb.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            int status = pst.executeUpdate();

            if (status == 1) {
                createSuccesAlert(type);

                refreshTable("SELECT * FROM proyects ORDER BY cod");
                clearButton();
                codeTb.requestFocus();
            } else {
                createErrorAlert(type);
            }
        } catch (SQLException e) {
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    public void searchByCodeButton()
    {
        dataBase.connect();
        refreshTable("SELECT * FROM proyects WHERE cod='"+ codeTb.getText() +"'");
        ObservableList<Project> items = projTb.getItems();
        if(!items.isEmpty()) {
            Actproject = items.get(0);
            loadTb();
        }
        dataBase.close();
    }

    @FXML
    private void allButton()
    {
        dataBase.connect();
        refreshTable("SELECT * FROM proyects ORDER BY cod");
        dataBase.close();
    }

    public void setStateInProgress() {stateLb.setText("in progress");}
    public void setStateOnHold() {stateLb.setText("on hold");}
    public void setStateCompleted() {stateLb.setText("completed");}

    /*------------ ALERTs ------------*/

    public void createErrorAlert(String type){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fail");
        alert.setHeaderText("PROJECT MANAGEMENT");
        alert.setContentText("Project " + type + " Failed");
        alert.showAndWait();
    }

    public void createSuccesAlert(String type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("PROJECT MANAGEMENT");
        alert.setContentText("Project " + type + " Successfully");
        alert.showAndWait();
    }
}
