package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.CustomAlert;
import com.example.gestionproyectos.clases.Comment;
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
/**
 * Class that controls the comment window
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class CommentController implements Initializable {
    private final String className = "Comment";
    Task actTask;
    Project actProject;
    Comment actComment;

    @FXML
    private TextArea contentTb;
    @FXML
    private TextField codeTb;
    @FXML
    private TextField taskTb;

    @FXML
    private TableView projectsTv;
    @FXML
    private TableColumn<Project, String> pCode;
    @FXML
    private TableColumn <Project, String> pTitle;

    @FXML
    private TableView tasksTv;
    @FXML
    private TableColumn <Task, String> tProject;
    @FXML
    private TableColumn <Task, String> tTitle;
    @FXML
    private TableColumn <Task, String> tCode;

    @FXML
    private TableView commentsTv;
    @FXML
    private TableColumn <Comment, String> cCode;
    @FXML
    private TableColumn <Comment, String> cTask;
    @FXML
    private TableColumn <Comment, String> cContent;

    /*--------------  LOAD DATA  --------------*/
    /**
     * Method that loads the data from the database to the table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        contentTb.setWrapText(true);
        if(dataBase.connect()) {
            refreshProjectsTable("SELECT * FROM proyects ORDER BY cod");
            ObservableList<Project> pItems = projectsTv.getItems();
            actProject = pItems.get(0);

            refreshTasksTable("SELECT * FROM tasks ORDER BY cod");
            ObservableList<Task> tItems = tasksTv.getItems();
            actTask = tItems.get(0);

            refreshCommentsTable("SELECT * FROM comments ORDER BY cod");
            ObservableList<Comment> cItems = commentsTv.getItems();
            actComment = cItems.get(0);

            loadTb();
        }
        else CustomAlert.createErrorAlert("MySql Connection", className);
    }

    /**
     * Method that refresh the comments table
     * @param sql
     */
    private void refreshCommentsTable(String sql) {
        String type = "SEARCH";
        ObservableList<Comment> comments = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Comment comment = new Comment();
                comment.setCode(rs.getString("cod"));
                comment.setContent(rs.getString("content"));
                comment.setCod_task(rs.getString("cod_task"));
                comments.add(comment);
            }
            commentsTv.setItems(comments);
            if(!comments.isEmpty())
                actComment = comments.get(0);

            cCode.setCellValueFactory(f -> f.getValue().codeProperty());
            cContent.setCellValueFactory(f -> f.getValue().contentProperty());
            cTask.setCellValueFactory(f -> f.getValue().cod_taskProperty());
        } catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method that refresh the task table
     * @param sql
     */
    private void refreshTasksTable(String sql) {
        String type = "SEARCH";
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try{
            dataBase.pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setCode(rs.getString("cod"));
                task.setTitle(rs.getString("title"));
                task.setProject(rs.getString("cod_proyect"));
                tasks.add(task);
            }
            tasksTv.setItems(tasks);
            if(tasks != null)
                actTask = tasks.get(0);
            loadComments();
            tCode.setCellValueFactory(f -> f.getValue().codeProperty());
            tTitle.setCellValueFactory(f -> f.getValue().titleProperty());
            tProject.setCellValueFactory(f -> f.getValue().projectProperty());
        } catch (Exception e){
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Method that refresh the project table
     * @param sql
     */
    private void refreshProjectsTable(String sql) {
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
            CustomAlert.createErrorAlert(type, className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Method that loads the tasks data from the database to the table
     */
    public void loadTasks()
    {
        refreshTasksTable("SELECT * FROM tasks WHERE cod_proyect='" + actProject.getCode() +"'");
    }
    /**
     * Method that loads the comments data from the database to the table
     */
    public void loadComments() {
        refreshCommentsTable("SELECT * FROM comments WHERE cod_task='" + actTask.getCode() +"'");
    }
    /**
     * Method that loads the data from the classes to the text fields
     */
    public void loadTb() {
        codeTb.setText(actComment.getCode());
        contentTb.setText(actComment.getContent());
        taskTb.setText(actComment.getCod_task());
    }

    /**
     * Method  that loads the project data to the classes when user clicks on the table
     */
    @FXML
    public void click_ProjectView() {
        Project selectedItem = (Project) projectsTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actProject = selectedItem;
            loadTasks();
        }
    }
    /**
     * Method  that loads the task data to the classes when user clicks on the table
     */
    @FXML
    public void click_TaskView() {
        Task selectedItem = (Task) tasksTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actTask = selectedItem;
            loadComments();
        }
    }
    /**
     * Method  that loads the comment data to the classes when user clicks on the table
     */
    public void click_CommentView() {
        Comment selectedItem = (Comment) commentsTv.getSelectionModel().getSelectedItem();
        if(selectedItem != null) {
            actComment = selectedItem;
            loadTb();
        }
    }

    /*--------------  BUTTONS  --------------*/
    /**
     * Method that clears the text fields
     */
    public void clearButton() {
        codeTb.setText("");
        taskTb.setText("");
        contentTb.setText("");
    }
    /**
     * Method that adds a new comment to the database
     */
    @FXML
    public void addBt() {
        String type = "INSERT";
        String sql = "INSERT INTO comments(cod, cod_task, content) values (?,?,?)";

        String code = codeTb.getText();
        String task = taskTb.getText();
        String content = contentTb.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            pst.setString(2, task);
            pst.setString(3, content);
            int status = pst.executeUpdate();

            if (status == 1) {
                CustomAlert.createSuccesAlert(type, className);
                loadTasks();
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
    /**
     * Method that updates a comment from the database
     */
    public void updateBt(){
        String type = "UPDATE";
        String sql = "UPDATE comments SET content=? WHERE cod=?";

        String code = codeTb.getText();
        String cont = contentTb.getText();

        try{
            pst = con.prepareStatement(sql);
            pst.setString(2, code);
            pst.setString(1, cont);

            int status = pst.executeUpdate();

            if(status == 1){
                CustomAlert.createSuccesAlert(type, className);
                loadTasks();
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
    /**
     * Method that deletes a comment from the database
     */
    public void deleteComment() {
        String type = "DELETE";
        String sql = "DELETE FROM comments WHERE cod=?";

        String code = codeTb.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, code);
            int status = pst.executeUpdate();

            if (status == 1) {
                CustomAlert.createSuccesAlert(type, className);
                allComments();
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

    /**
     * Method that refreshes all the tables
     */
    @FXML
    private void allComments() {
        refreshCommentsTable("SELECT * FROM comments ORDER BY cod");
    }
    /**
     * Method that searches a comment by code
     */
    public void searchCommentByCodeButton() {
        refreshCommentsTable("SELECT * FROM comments WHERE cod='"+ codeTb.getText() +"'");
        ObservableList<Comment> items = commentsTv.getItems();
        if(!items.isEmpty()) {
            actComment = items.get(0);
            loadTb();
        }
        refreshCommentsTable("SELECT * FROM comments WHERE cod='"+ codeTb.getText() +"'");
    }
    /**
     * Method that searches a comments by task code
     */
    public void searchTaskByCodeButton() {
        refreshCommentsTable("SELECT * FROM comments WHERE cod_task='"+ taskTb.getText() +"'");
        ObservableList<Comment> items = commentsTv.getItems();
        if(!items.isEmpty()) {
            actComment = items.get(0);
            loadTb();
        }
    }
}
