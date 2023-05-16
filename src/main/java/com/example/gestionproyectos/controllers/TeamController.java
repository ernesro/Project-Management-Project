package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.*;
import com.example.gestionproyectos.data.dataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.gestionproyectos.data.dataBase.con;
import static com.example.gestionproyectos.data.dataBase.pst;
/**
 * Class that controls the team window
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class TeamController extends CustomAlert implements Initializable {
    private final String className = "Team";
    Team actTeam;

    @FXML
    private TextField nameTb;
    @FXML
    private TextField codeTb;

    @FXML
    private TableView teamsTv;
    @FXML
    private TableColumn <Team, String> tName;
    @FXML
    private TableColumn <Team, String> tCode;

    /*--------------  LOAD DATA  --------------*/
    /**
     * Method that loads the data from the database
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dataBase.connect()) {
            refreshTeamsTable("SELECT * FROM teams ORDER BY cod");
            ObservableList<Team> tItems = teamsTv.getItems();
            actTeam = tItems.get(0);
            loadTb();
        }
        else CustomAlert.createErrorAlert("MySql Connection", className);
    }

    /**
     * Method that loads the text fields with the class data
     */
    public void loadTb() {
        codeTb.setText(actTeam.getCode());
        nameTb.setText(actTeam.getName());
    }

    /**
     * Method that refreshes the table with the data from the database
     * @param sql
     */
    private void refreshTeamsTable(String sql) {
        String type = "SEARCH";
        ObservableList<Team> teams = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Team team = new Team();
                team.setCode(rs.getString("cod"));
                team.setName(rs.getString("name"));
                teams.add(team);
            }
            teamsTv.setItems(teams);
            if(!teams.isEmpty()) {
                actTeam = teams.get(0);
                loadTb();
            }
            tCode.setCellValueFactory(f -> f.getValue().codeProperty());
            tName.setCellValueFactory(f -> f.getValue().nameProperty());
        } catch (Exception e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }


    /*--------------  BUTTONS  --------------*/

    /**
     * Method that clears the text fields
     */
    public void clearBt() {
        codeTb.setText("");
        nameTb.setText("");
    }

    /**
     * Method that adds a new team to the database
     */
    @FXML
    public void addBt() {
        String type = "INSERT";
        String sql = "INSERT INTO teams(cod, name) values (?,?)";

        String code = codeTb.getText();
        String name = nameTb.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            pst.setString(2, name);
            int status = pst.executeUpdate();

            if (status == 1) {
                CustomAlert.createSuccesAlert(type, className);
                allTeamsButton();
                clearBt();
                codeTb.requestFocus();
            } else {
                CustomAlert.createErrorAlert(type, className);
            }
        } catch (SQLException e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method that updates the team data in the database
     */
    public void updateBt(){
        String type = "UPDATE";
        String sql = "UPDATE teams SET name=? WHERE cod=?";

        String code = codeTb.getText();
        String name = nameTb.getText();

        try{
            pst = con.prepareStatement(sql);
            pst.setString(2, code);
            pst.setString(1, name);

            int status = pst.executeUpdate();

            if(status == 1){
                CustomAlert.createSuccesAlert(type, className);
                allTeamsButton();
                clearBt();
                codeTb.requestFocus();
            } else {
                CustomAlert.createErrorAlert(type, className);
            }
        } catch (SQLException e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method that deletes the team from the database
     */
    public void deleteBt() {
        String type = "DELETE";
        String sql = "DELETE FROM teams WHERE cod=?";
        String code = codeTb.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            int status = pst.executeUpdate();

            if (status == 1) {
                CustomAlert.createSuccesAlert(type, className);
                allTeamsButton();
                clearBt();
                codeTb.requestFocus();
            } else {
                CustomAlert.createErrorAlert(type, className);
            }
        } catch (SQLException e) {
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method that searches the team by the code
     */
    public void searchTeamByCodeButton() {
        refreshTeamsTable("SELECT * FROM teams WHERE cod='"+ codeTb.getText() +"'");
        ObservableList<Team> items = teamsTv.getItems();
        if(!items.isEmpty()) {
            actTeam = items.get(0);
            loadTb();
        }
    }

    /**
     * Method that shows all teams
     */
    public void allTeamsButton() {
        refreshTeamsTable("SELECT * FROM teams ORDER BY cod");
    }

    /**
     * Method that loads team when clicked on the table
     */
    @FXML
    public void click_TeamView() {
        Team selectedItem = (Team) teamsTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actTeam = selectedItem;
            loadTb();
        }
    }
}
