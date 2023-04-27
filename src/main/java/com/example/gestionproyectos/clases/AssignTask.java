package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AssignTask
{
    private final StringProperty employee;
    private final StringProperty task;

    public AssignTask() {
        employee = new SimpleStringProperty(this, "employee");
        task = new SimpleStringProperty(this, "task");
    }

    public StringProperty employeeProperty() {return employee; }
    public String getEmployee() {return employee.getValue();}
    public void setEmployee(String state) {this.employee.setValue(state);}

    public StringProperty taskProperty() {return task; }
    public String getTask() {return task.getValue();}
    public void setTask(String state) {this.task.setValue(state);}
}
