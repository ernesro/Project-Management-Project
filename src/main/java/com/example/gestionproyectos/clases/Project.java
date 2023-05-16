package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;

/**
 * Class that represents a project
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class Project
{
    /**
     * Constructor of the class
     * @param code string that represents the code of the project
     * @param title string that represents the title of the project
     * @param description string that represents the description of the project
     * @param state string that represents the state of the project
     */
    private final javafx.beans.property.StringProperty code;
    private final javafx.beans.property.StringProperty title;
    private final javafx.beans.property.StringProperty description;
    private final javafx.beans.property.StringProperty state;

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
