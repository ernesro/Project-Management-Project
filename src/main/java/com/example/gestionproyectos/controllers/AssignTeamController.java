package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.CustomAlert;
import com.example.gestionproyectos.clases.AssignTeam;
import com.example.gestionproyectos.clases.Project;
import com.example.gestionproyectos.clases.Team;
import com.example.gestionproyectos.data.dataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.gestionproyectos.data.dataBase.con;
import static com.example.gestionproyectos.data.dataBase.pst;

public class AssignTeamController extends CustomAlert implements Initializable {
    private final String className = "Assign Team";
    private Project project;
    private Team team;

    @FXML
    private TextField codeTb;
    @FXML
    private TextField projectTb;

    @FXML
    private TableView teamsTv;
    @FXML
    private TableColumn<Team, String> codeTeamColum;
    @FXML
    private TableColumn <Team, String> nameTeamColum;

    @FXML
    private TableView projTv;
    @FXML
    private TableColumn <Project, String> codeColum;
    @FXML
    private TableColumn <Project, String> titleColum;

    @FXML
    private TableView assigTv;
    @FXML
    private TableColumn <AssignTeam, String> tC;
    @FXML
    private TableColumn <AssignTeam, String> pC;

    /*----------------LOAD DATA----------------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dataBase.connect()) {
            refreshTeamsTable("SELECT * FROM teams ORDER BY cod");
            ObservableList<Team> tItems = teamsTv.getItems();
            team = tItems.get(0);

            refreshTable("SELECT * FROM proyects ORDER BY cod");
            ObservableList<Project> items = projTv.getItems();
            project = items.get(0);

            refreshAssignTable("SELECT * FROM assignteam");

            loadTeam();
            loadProject();
        }
        else CustomAlert.createErrorAlert("MySql Connection",className);
    }

    public void loadTeam() { codeTb.setText(team.getCode()); }
    public void loadProject() { projectTb.setText(project.getCode()); }

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
                team = teams.get(0);
                loadTeam();
            }
            codeTeamColum.setCellValueFactory((f -> f.getValue().codeProperty()));
            nameTeamColum.setCellValueFactory(f -> f.getValue().nameProperty());
        } catch (Exception e){
            CustomAlert.createErrorAlert(type,className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void refreshAssignTable(String sql) {
        String type = "SEARCH";
        ObservableList<AssignTeam> aTeams = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                AssignTeam aTeam = new AssignTeam();
                aTeam.setCod_team(rs.getString("cod_team"));
                aTeam.setCod_project(rs.getString("cod_proyect"));
                aTeams.add(aTeam);
            }
            assigTv.setItems(aTeams);
            if(!aTeams.isEmpty()) {
                loadTb();
            }
            tC.setCellValueFactory(f -> f.getValue().cod_teamProperty());
            pC.setCellValueFactory(f -> f.getValue().cod_projectProperty());
        } catch (Exception e){
            CustomAlert.createErrorAlert(type,className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void loadTb() {
        projectTb.setText(project.getCode());
        codeTb.setText(team.getCode());
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
                projects.add(project);
            }

            projTv.setItems(projects);
            codeColum.setCellValueFactory(f -> f.getValue().codeProperty());
            titleColum.setCellValueFactory(f -> f.getValue().titleProperty());

        } catch (Exception e){
            CustomAlert.createErrorAlert(type,className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @FXML
    public void click_TeamView() {
        Team selectedItem = (Team) teamsTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            team = selectedItem;
            loadTeam();
        }
    }

    public void click_AssignView() {
        AssignTeam selectedItem = (AssignTeam) assigTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            codeTb.setText(selectedItem.getCod_team());
            projectTb.setText(selectedItem.getCod_project());
        }
    }

    public void loadTbFromSelectedTableView() {
        Project selectedItem = (Project) projTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            project = selectedItem;
            loadProject();
        }
    }

    /*----------------BUTTONS----------------*/

    public void clearBt() {
        codeTb.setText("");
        projectTb.setText("");
    }

    @FXML
    public void addBt() {
        String type = "INSERT";
        String sql = "INSERT INTO assignteam(cod_team, cod_proyect) values (?,?)";
        String team = codeTb.getText();
        String project = projectTb.getText();
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, team);
            pst.setString(2,project);
            int status = pst.executeUpdate();

            if(status == 1){
                CustomAlert.createSuccesAlert(type,className);
                refreshAssignTable("select * from assignteam");
                clearBt();
                codeTb.requestFocus();
            } else {
                CustomAlert.createErrorAlert(type,className);
            }
        } catch (SQLException e){
            CustomAlert.createErrorAlert(type,className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void deleteBt() {
        String type = "DELETE";
        String sql = "DELETE FROM assignteam WHERE cod_team=? AND cod_proyect=?";
        String code = codeTb.getText();
        String project = projectTb.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            pst.setString(2,project);
            int status = pst.executeUpdate();

            if (status == 1) {
                CustomAlert.createSuccesAlert(type, className);
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

    public void SearchByProjectBt() {
        refreshAssignTable("SELECT * FROM assignteam WHERE cod_proyect = '" + projectTb.getText() + "'");
    }

    public void SearchByTeam() {
        refreshAssignTable("SELECT * FROM assignteam WHERE cod_team = '" + codeTb.getText() + "'");
    }

    public void ShowAll() {
        refreshAssignTable("SELECT * FROM assignteam");
    }
}
