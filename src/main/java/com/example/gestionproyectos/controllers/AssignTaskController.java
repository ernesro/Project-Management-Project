package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.clases.AssignTask;
import com.example.gestionproyectos.clases.CustomAlert;
import com.example.gestionproyectos.clases.Employee;
import com.example.gestionproyectos.clases.Task;
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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.gestionproyectos.data.dataBase.con;
import static com.example.gestionproyectos.data.dataBase.pst;
/**
 * Class that controls the assign task window
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class AssignTaskController extends CustomAlert implements Initializable {
    private final String className = "Task Assign";
    private Employee employee;
    private Task task;

    @FXML
    private TextField employeeTb;
    @FXML
    private TextField taskTb;

    @FXML
    private TableView tasksTv;
    @FXML
    private TableColumn <Task, String> taskCodeColumn;
    @FXML
    private TableColumn <Task, String> taskTitleColumn;

    @FXML
    private TableView employeesTv;
    @FXML
    private TableColumn <Employee, String> employeeDniColumn;
    @FXML
    private TableColumn <Employee, String> employeeNameColumn;
    @FXML
    private TableColumn <Employee, String> employeeLNameColumn;

    @FXML
    private TableView assignTaskTv;
    @FXML
    private TableColumn <AssignTask, String> assignTaskColumn;
    @FXML
    private TableColumn <AssignTask, String> assignEmployeeColumn;

    /*----------------LOAD DATA----------------*/
    /**
     * Method that loads the data from the database to the table
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(dataBase.connect()){
           refreshTaskTable("SELECT * FROM tasks");
           ObservableList<Task> tItems = tasksTv.getItems();
           task = tItems.get(0);

           refreshEmployeeTable("SELECT * FROM employees");
           ObservableList<Employee> eItems = employeesTv.getItems();
           employee = eItems.get(0);

           refreshAssignTaskTable("SELECT * FROM assigntask");

        }
        else { CustomAlert.createErrorAlert("CONNECT",className); }
    }
    /**
     * Method that refreshes the assign task table
     * @param sql SQL query
     */
    private void refreshAssignTaskTable(String sql) {
        String type = "SEARCH";

        ObservableList<AssignTask> list = FXCollections.observableArrayList();
        try{
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                AssignTask assignTask = new AssignTask();
                assignTask.setEmployee(rs.getString("cod_task"));
                assignTask.setTask(rs.getString("dni"));
                list.add(assignTask);
            }
            assignTaskTv.setItems(list);
            assignTaskColumn.setCellValueFactory(cellData -> cellData.getValue().taskProperty());
            assignEmployeeColumn.setCellValueFactory(cellData -> cellData.getValue().employeeProperty());
        } catch (SQLException e) {
            CustomAlert.createErrorAlert(type,className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Method that refreshes the employee table
     * @param sql SQL query
     */
    private void refreshEmployeeTable(String sql) {
        String type = "SEARCH";
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        try{
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Employee employee = new Employee();
                employee.setDni(rs.getString("dni"));
                employee.setName(rs.getString("name"));
                employee.setLastName(rs.getString("lastName"));
                employeeList.add(employee);
            }
            employeesTv.setItems(employeeList);

            employeeDniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
            employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            employeeLNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastnameProperty());
        } catch (SQLException e) {
            CustomAlert.createErrorAlert(type,className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Method that refreshes the task table
     * @param sql SQL query
     */
    public void refreshTaskTable(String sql) {
        String type = "SEARCH";

        ObservableList<Task> taskList = FXCollections.observableArrayList();
        try{
            pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                Task task = new Task();
                task.setCode(rs.getString("cod"));
                task.setTitle(rs.getString("title"));
                taskList.add(task);
            }
            tasksTv.setItems(taskList);

            taskCodeColumn.setCellValueFactory(cellData -> cellData.getValue().codeProperty());
            taskTitleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        } catch (SQLException e) {
            CustomAlert.createErrorAlert(type,className);
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    /**
     * Method that loads the assign task data when clicked on the table
     */
    public void assignTvClick() {
        AssignTask selectedAssignTask = (AssignTask) assignTaskTv.getSelectionModel().getSelectedItem();
        if(selectedAssignTask != null){
            employeeTb.setText(selectedAssignTask.getTask());
            taskTb.setText(selectedAssignTask.getEmployee());
        }
    }
    /**
     * Method that loads the task data when clicked on the table
     */
    public void taskTvClick() {
        Task selectedTask = (Task) tasksTv.getSelectionModel().getSelectedItem();
        if(selectedTask != null){
           task = selectedTask;
           loadTask();
        }
    }

    /**
     * Method that loads the task data to the text field
     */
    private void loadTask() { taskTb.setText(task.getCode()); }
    /**
     * Method that loads the employee data when clicked on the table
     */
    public void employeesTvClick() {
        Employee selectedEmployee = (Employee) employeesTv.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null){
            employee = selectedEmployee;
            loadEmployee();
        }
    }
    /**
     * Method that loads the employee data to the text field
     */
    private void loadEmployee() { employeeTb.setText(employee.getDni()); }

    /*----------------BUTTONS----------------*/

    /**
     * Method that adds a new assign task to the database
     */
    public void addBtnClick() {
        String type = "INSERT";
        String sql = "INSERT INTO assigntask (cod_task, dni) VALUES (?,?)";
        if(!employeeTb.getText().isEmpty() && !taskTb.getText().isEmpty()){
            try{
                pst = con.prepareStatement(sql);
                pst.setString(1, task.getCode());
                pst.setString(2, employee.getDni());
                int status = pst.executeUpdate();

                if(status == 1) {
                    CustomAlert.createSuccesAlert(type, className);
                    refreshAssignTaskTable("SELECT * FROM assigntask");
                } else {
                    CustomAlert.createErrorAlert(type, className);
                }
            } catch (SQLException e) {
                CustomAlert.createErrorAlert(type,className);
                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
            }
        }else{
            CustomAlert.createErrorAlert(type,className);
        }
    }

    /**
     * Method that deletes an assign task from the database
     */
    public void deleteBtnClick(){
        String type = "DELETE";
        String sql = "DELETE FROM assigntask WHERE cod_task = ? AND dni = ?";
        if(!employeeTb.getText().isEmpty() && !taskTb.getText().isEmpty()){
            try{
                pst = con.prepareStatement(sql);
                pst.setString(1, task.getCode());
                pst.setString(2, employee.getDni());
                int status = pst.executeUpdate();
                if(status == 1) {
                    CustomAlert.createSuccesAlert(type, className);
                    refreshAssignTaskTable("SELECT * FROM assigntask");
                } else {
                    CustomAlert.createErrorAlert(type, className);
                }
            } catch (SQLException e) {
                CustomAlert.createErrorAlert(type,className);
                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, e);
            }
        }else{
            CustomAlert.createErrorAlert(type,className);
        }
    }
    /**
     * Method that clears the text fields
     */
    public void clearBtnClick() {
        employeeTb.setText("");
        taskTb.setText("");
    }
    /**
     * Method that searches a task in the task table
     */
    public void SearchInTaskTable() { refreshTaskTable("SELECT * FROM tasks WHERE cod = '" + taskTb.getText() + "'"); }
    /**
     * Method that searches an employee in the employee table
     */
    public void SearchInEmployeeTable() {refreshEmployeeTable("SELECT * FROM employees WHERE dni = '" + employeeTb.getText() + "'");}

    /**
     * Method that refreshes all the tables
     */
    public void ShowAllBt() {
        refreshAssignTaskTable("SELECT * FROM assigntask");
        refreshEmployeeTable("SELECT * FROM employees");
        refreshTaskTable("SELECT * FROM tasks");
    }
    /**
     * Method that searches an employee in the assign task table
     */
    public void SearchInAssignTaskByEmployee() {
        refreshAssignTaskTable("SELECT * FROM assigntask WHERE dni = '" + employeeTb.getText() + "'");
    }
    /**
     * Method that searches a task in the assign task table
     */
    public void SearchInAssignTaskByTask() {
        refreshAssignTaskTable("SELECT * FROM assigntask WHERE cod_task = '" + taskTb.getText() + "'");
    }
}
