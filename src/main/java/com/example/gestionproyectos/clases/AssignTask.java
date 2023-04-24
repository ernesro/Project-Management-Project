package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AssignTask
{
    private final StringProperty employee;
    private final StringProperty project;

    public AssignTask() {
        employee = new SimpleStringProperty(this, "employee");
        project = new SimpleStringProperty(this, "project");
    }

    public StringProperty employeeProperty() {return employee; }
    public String getEmployee() {return employee.getValue();}
    public void setEmployee(String state) {this.employee.setValue(state);}

    public StringProperty projectProperty() {return project; }
    public String getProject() {return project.getValue();}
    public void setProject(String project) {this.project.setValue(project);}
}
