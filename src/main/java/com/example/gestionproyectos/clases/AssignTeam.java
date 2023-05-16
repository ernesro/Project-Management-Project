package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;
/**
 * Class that represents the assignation of a team to a project
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class AssignTeam
{
    /**
     * Constructor of the class
     * @param cod_team Team assigned to the project
     * @param cod_project Project to which the team is assigned
     */
    private final javafx.beans.property.StringProperty cod_team;
    private final javafx.beans.property.StringProperty cod_project;

    public AssignTeam() {
        cod_team = new SimpleStringProperty(this, "cod_team");
        cod_project = new SimpleStringProperty(this, "cod_project");
    }

    public javafx.beans.property.StringProperty cod_teamProperty(){return cod_team;}
    public String getCod_team(){return cod_team.getValue();}
    public void setCod_team(String c){cod_team.setValue(c);}

    public javafx.beans.property.StringProperty cod_projectProperty(){return cod_project;}
    public String getCod_project(){return cod_project.getValue();}
    public void setCod_project(String c){cod_project.setValue(c);}
}
