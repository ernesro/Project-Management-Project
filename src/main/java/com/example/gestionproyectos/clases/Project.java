package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;
public class Project
{
    private final javafx.beans.property.StringProperty code;
    private final javafx.beans.property.StringProperty title;
    private javafx.beans.property.StringProperty description;
    private javafx.beans.property.StringProperty state;

    public Project() {
        code = new SimpleStringProperty(this, "code");
        title = new SimpleStringProperty(this, "tilte");
        description = new SimpleStringProperty(this, "description");
        state = new SimpleStringProperty(this, "state");
    }


    public javafx.beans.property.StringProperty codeProperty(){return code;}
    public String getCode(){return code.getValue();}
    public void setCode(String newName){code.setValue(newName);}

    public javafx.beans.property.StringProperty titleProperty() { return title; }
    public String getTitle() { return title.getValue(); }
    public void setTitle(String title) { this.title.setValue(title); }

    public javafx.beans.property.StringProperty descriptionProperty() {return description;}
    public String getDescription() { return description.getValue();}
    public void setDescription(String description) {this.description.setValue(description); }

    public javafx.beans.property.StringProperty stateProperty() {return state; }
    public String getState() {return state.getValue();}
    public void setState(String state) {this.state.setValue(state);}
}
