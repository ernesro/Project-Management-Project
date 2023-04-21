package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;

public class Team
{
    private final javafx.beans.property.StringProperty code;
    private final javafx.beans.property.StringProperty name;
    private final javafx.beans.property.StringProperty cod_project;

    public Team() {
        code = new SimpleStringProperty(this, "code");
        name = new SimpleStringProperty(this, "name");
        cod_project = new SimpleStringProperty(this, "cod_project");
    }

    public javafx.beans.property.StringProperty codeProperty(){return code;}
    public String getCode(){return code.getValue();}
    public void setCode(String c){code.setValue(c);}

    public javafx.beans.property.StringProperty nameProperty(){return name;}
    public String getName(){return name.getValue();}
    public void setName(String c){name.setValue(c);}

    public javafx.beans.property.StringProperty cod_projectProperty(){return cod_project;}
    public String getCod_project(){return cod_project.getValue();}
    public void setCod_project(String c){cod_project.setValue(c);}
}
