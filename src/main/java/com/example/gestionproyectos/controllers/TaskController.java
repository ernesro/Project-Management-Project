package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.Project;
import com.example.gestionproyectos.clases.Task;
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

public class TaskController implements Initializable
{
    Task actTask;
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
    private TextField prjtTb;

    @FXML
    private TableView projetTv;
    @FXML
    private TableColumn<Project, String> pCodeColum;
    @FXML
    private TableColumn <Project, String> pTitleColum;

    @FXML
    private TableView taskTv;
    @FXML
    private TableColumn <Task, String> tDescColum;
    @FXML
    private TableColumn <Task, String> tStateColum;
    @FXML
    private TableColumn <Task, String> tTitleColum;
    @FXML
    private TableColumn <Task, String> tCodeColum;

    /*--------  LOAD DATA  --------*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        descTb.setWrapText(true);
        dataBase.connect();

        refreshProjectTable("SELECT * FROM proyects ORDER BY cod");
        ObservableList<Project> pItems = projetTv.getItems();
        actProject = pItems.get(0);

        refreshTaskTable("SELECT * FROM tasks ORDER BY cod");
        ObservableList<Task> tItems = taskTv.getItems();
        actTask = tItems.get(0);

        loadTb();
        dataBase.close();
    }

    public void refreshProjectTable(String sql)
    {
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
            projetTv.setItems(projects);
            pCodeColum.setCellValueFactory(f -> f.getValue().codeProperty());
            pTitleColum.setCellValueFactory(f -> f.getValue().titleProperty());
        } catch (Exception e){
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    public void refreshTaskTable(String sql)
    {
        dataBase.connect();
        String type = "SEARCH";
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setCode(rs.getString("cod"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setProject(rs.getString("cod_proyect"));
                task.setState(rs.getString("state"));
                tasks.add(task);
            }
            taskTv.setItems(tasks);
            if(tasks != null)
                actTask = tasks.get(0);
            loadTb();
            tCodeColum.setCellValueFactory(f -> f.getValue().codeProperty());
            tTitleColum.setCellValueFactory(f -> f.getValue().titleProperty());
            tDescColum.setCellValueFactory(f -> f.getValue().descriptionProperty());
            tStateColum.setCellValueFactory(f -> f.getValue().stateProperty());
        } catch (Exception e){
            createErrorAlert(type);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
        dataBase.close();
    }

    public void loadTb()
    {
        codeTb.setText(actTask.getCode());
        descTb.setText(actTask.getDescription());
        tltTb.setText(actTask.getTitle());
        prjtTb.setText(actTask.getProject());
        stateLb.setText(actTask.getState());
    }

    public void loadTasks()
    {
        dataBase.connect();
        refreshTaskTable("SELECT * FROM tasks WHERE cod_proyect='" + actProject.getCode() +"'");
        dataBase.close();
    }

    public void loadProjects()
    {
        dataBase.connect();
        refreshProjectTable("SELECT * FROM proyects WHERE cod='" + actTask.getProject() +"'");
        dataBase.close();
    }

    /*-----------   BUTTONS   -----------*/

    public void clearButton()
    {
        codeTb.setText("");
        descTb.setText("");
        tltTb.setText("");
        stateLb.setText("");
        prjtTb.setText("");
    }

    @FXML
    public void addTask()
    {
        String type = "INSERT";
        String sql = "INSERT INTO tasks(cod, title, cod_proyect, description, state) values (?,?,?,?,?)";
        dataBase.connect();
        String code = codeTb.getText();
        String title = tltTb.getText();
        String desc = descTb.getText();
        String proyect = prjtTb.getText();
        String state = stateLb.getText();
        try{
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            pst.setString(2, title);
            pst.setString(3,proyect);
            pst.setString(4, desc);
            pst.setString(5, state);
            int status = pst.executeUpdate();

            if(status == 1){
                createSuccesAlert(type);

                loadTasks();
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

    @FXML
    public void updateTask(){
        String type = "UPDATE";
        String sql = "UPDATE tasks SET title=?, description=?, state=? WHERE cod=?";
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

                loadTasks();
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

    public void deleteTask()
    {
        String type = "DELETE";
        String sql = "DELETE FROM tasks WHERE cod=?";
        dataBase.connect();

        String code = codeTb.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            int status = pst.executeUpdate();

            if (status == 1) {
                createSuccesAlert(type);

                allTasksButton();
                allProjectsButton();
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

    @FXML
    public void click_ProjectView()
    {
        Project selectedItem = (Project) projetTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actProject = selectedItem;
            loadTasks();
            loadTb();
        }
    }

    @FXML
    public void click_TaskView()
    {
        Task selectedItem = (Task) taskTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actTask = selectedItem;
            loadTb();
        }
    }

    @FXML
    public void searchProjectByCodeButton()
    {
        dataBase.connect();
        refreshProjectTable("SELECT * FROM proyects WHERE cod='"+ prjtTb.getText() +"'");
        ObservableList<Project> items = projetTv.getItems();
        if(!items.isEmpty()) {
            actProject = items.get(0);
            loadTb();
        }
        loadTasks();
        dataBase.close();
    }

    public void searchTaskByCodeButton()
    {
        dataBase.connect();
        refreshTaskTable("SELECT * FROM tasks WHERE cod='"+ codeTb.getText() +"'");
        ObservableList<Task> items = taskTv.getItems();
        if(!items.isEmpty()) {
            actTask = items.get(0);
            loadTb();
        }
        loadProjects();
        dataBase.close();
    }

    @FXML
    private void allTasksButton()
    {
        dataBase.connect();
        refreshTaskTable("SELECT * FROM tasks ORDER BY cod");
        dataBase.close();
    }

    @FXML
    private void allProjectsButton()
    {
        dataBase.connect();
        refreshProjectTable("SELECT * FROM proyects ORDER BY cod");
        dataBase.close();
    }

    @FXML
    public void setStateInProgress() {stateLb.setText("in progress");}
    @FXML
    public void setStateOnHold() {stateLb.setText("not started");}
    @FXML
    public void setStateCompleted() {stateLb.setText("completed");}

    /*-----------   ALERTS   -----------*/

    public void createErrorAlert(String type){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fail");
        alert.setHeaderText("TASK MANAGEMENT");
        alert.setContentText("Task " + type + " Failed");
        alert.showAndWait();
    }

    public void createSuccesAlert(String type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("TASK MANAGEMENT");
        alert.setContentText("Task " + type + " Successfully");
        alert.showAndWait();
    }
}