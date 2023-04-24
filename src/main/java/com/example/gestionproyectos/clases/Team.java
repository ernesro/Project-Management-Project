package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;

public class Team
{
    private final javafx.beans.property.StringProperty code;
    private final javafx.beans.property.StringProperty name;

    public Team() {
        code = new SimpleStringProperty(this, "code");
        name = new SimpleStringProperty(this, "name");
    }

    public javafx.beans.property.StringProperty codeProperty(){return code;}
    public String getCode(){return code.getValue();}
    public void setCode(String c){code.setValue(c);}

    public javafx.beans.property.StringProperty nameProperty(){return name;}
    public String getName(){return name.getValue();}
    public void setName(String c){name.setValue(c);}

}
