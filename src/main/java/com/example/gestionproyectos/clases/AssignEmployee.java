package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class to define a assigned employee to a team
 * @author ErnestasUrbonas
 * @version 2.0
 */
public class AssignEmployee
{
    /**
     * Constructor without parameters
     * @param employee a string with the employee dni
     * @param team a string with the team code
     */
    private final StringProperty employee;
    private final StringProperty team;
    public AssignEmployee() {
        employee = new SimpleStringProperty(this, "employee");
        team = new SimpleStringProperty(this, "team");
    }

    public StringProperty employeeProperty() {return employee; }
    public String getEmployee() {return employee.getValue();}
    public void setEmployee(String state) {this.employee.setValue(state);}

    public StringProperty teamProperty() {return team; }
    public String getTeam() {return team.getValue();}
    public void setTeam(String state) {this.team.setValue(state);}
}
