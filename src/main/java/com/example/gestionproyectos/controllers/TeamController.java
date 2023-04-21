package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.Comment;
import com.example.gestionproyectos.clases.Project;
import com.example.gestionproyectos.clases.Task;
import com.example.gestionproyectos.clases.Team;
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

public class TeamController implements Initializable
{
    Project actProject;
    Team actTeam;

    @FXML
    private TextField nameTb;
    @FXML
    private TextField codeTb;
    @FXML
    private TextField projectTb;

    @FXML
    private TableView projectsTv;
    @FXML
    private TableColumn<Project, String> pCode;
    @FXML
    private TableColumn <Project, String> pTitle;

    @FXML
    private TableView teamsTv;
    @FXML
    private TableColumn <Team, String> tProject;
    @FXML
    private TableColumn <Team, String> tName;
    @FXML
    private TableColumn <Team, String> tCode;

    /*--------------  LOAD DATA  --------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dataBase.connect()) {
            refreshProjectsTable("SELECT * FROM proyects ORDER BY cod");
            ObservableList<Project> pItems = projectsTv.getItems();
            actProject = pItems.get(0);

            refreshTeamsTable("SELECT * FROM teams ORDER BY cod");
            ObservableList<Team> tItems = teamsTv.getItems();
            actTeam = tItems.get(0);

            loadTb();
            dataBase.close();
        }
        else createErrorAlert("MySql Connection");
    }

    public void loadTb()
    {
        codeTb.setText(actTeam.getCode());
        nameTb.setText(actTeam.getName());
        projectTb.setText(actTeam.getCod_project());
    }

    private void refreshTeamsTable(String sql) {
        dataBase.connect();
        String type = "SEARCH";
        ObservableList<Team> teams = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Team team = new Team();
                team.setCode(rs.getString("cod"));
                team.setName(rs.getString("name"));
                team.setCod_project(rs.getString("cod_proyect"));
                teams.add(team);
            }
            teamsTv.setItems(teams);
            if(!teams.isEmpty()) {
                actTeam = teams.get(0);
                loadTb();
            }
            tCode.setCellValueFactory(f -> f.getValue().codeProperty());
            tName.setCellValueFactory(f -> f.getValue().nameProperty());
            tProject.setCellValueFactory(f -> f.getValue().cod_projectProperty());
        } catch (Exception e){
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    private void refreshProjectsTable(String sql) {
        dataBase.connect();
        String type = "SEARCH";
        ObservableList<Project> projects = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Project project = new Project();
                project.setCode(rs.getString("cod"));
                project.setTitle(rs.getString("title"));
                projects.add(project);
            }
            projectsTv.setItems(projects);
            pCode.setCellValueFactory(f -> f.getValue().codeProperty());
            pTitle.setCellValueFactory(f -> f.getValue().titleProperty());
        } catch (Exception e){
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    public void loadTeams()
    {
        dataBase.connect();
        refreshTeamsTable("SELECT * FROM teams WHERE cod_proyect='" + actProject.getCode() +"'");
        dataBase.close();
    }

    /*--------------  BUTTONS  --------------*/

    public void clearBt()
    {
        codeTb.setText("");
        nameTb.setText("");
        projectTb.setText("");
    }

    @FXML
    public void addBt()
    {
        String type = "INSERT";
        String sql = "INSERT INTO teams(cod, cod_proyect, name) values (?,?,?)";
        dataBase.connect();
        String code = codeTb.getText();
        String name = nameTb.getText();
        String proj = projectTb.getText();
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            pst.setString(2, proj);
            pst.setString(3,name);
            int status = pst.executeUpdate();

            if(status == 1){
                createSuccesAlert(type);

                loadTeams();
                clearBt();
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

    public void updateBt(){
        String type = "UPDATE";
        String sql = "UPDATE teams SET name=? WHERE cod=?";
        dataBase.connect();

        String code = codeTb.getText();
        String name = nameTb.getText();

        try{
            pst = con.prepareStatement(sql);
            pst.setString(2, code);
            pst.setString(1, name);

            int status = pst.executeUpdate();

            if(status == 1){
                createSuccesAlert(type);

                loadTeams();
                clearBt();
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

    public void deleteBt() {
        String type = "DELETE";
        String sql = "DELETE FROM teams WHERE cod=?";
        dataBase.connect();
        String code = codeTb.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            int status = pst.executeUpdate();

            if (status == 1) {
                createSuccesAlert(type);

                allTeamsButton();
                clearBt();
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

    public void searchTeamByCodeButton()
    {
        dataBase.connect();
        refreshTeamsTable("SELECT * FROM teams WHERE cod='"+ codeTb.getText() +"'");
        ObservableList<Team> items = teamsTv.getItems();
        if(!items.isEmpty()) {
            actTeam = items.get(0);
            loadTb();
        }
        dataBase.close();
    }

    public void searchProjectByCodeButton()
    {
        dataBase.connect();
        refreshProjectsTable("SELECT * FROM proyects WHERE cod='"+ projectTb.getText() +"'");
        ObservableList<Project> items = projectsTv.getItems();
        if(!items.isEmpty()) {
            actProject = items.get(0);
            loadTeams();
        }
        dataBase.close();
    }

    public void allTeamsButton()
    {
        refreshTeamsTable("SELECT * FROM teams ORDER BY cod");
    }
    public void allProjectsButton()
    {
        refreshProjectsTable("SELECT * FROM proyects ORDER BY cod");
    }

    @FXML
    public void click_ProjectView()
    {
        Project selectedItem = (Project) projectsTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actProject = selectedItem;
            loadTeams();
        }
    }

    @FXML
    public void click_TeamView()
    {
        Team selectedItem = (Team) teamsTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actTeam = selectedItem;
            loadTb();
        }
    }

    /*------------ ALERTS ------------*/

    public void createErrorAlert(String type){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fail");
        alert.setHeaderText("Team MANAGEMENT");
        alert.setContentText("Team " + type + " Failed");
        alert.showAndWait();
    }

    public void createSuccesAlert(String type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Team MANAGEMENT");
        alert.setContentText("Team " + type + " Successfully");
        alert.showAndWait();
    }
}
