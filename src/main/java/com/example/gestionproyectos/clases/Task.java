package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Class that represents a task
 * @version 2.0
 * @author Ernestas Urbonas
 */
public class Task
{
    /**
     * Constructor of the class
     * @param code string that represents the code of the task
     * @param title string that represents the title of the task
     * @param description string that represents the description of the task
     * @param state string that represents the state of the task
     * @param project string that represents the assigned project of the task
     */
    private final javafx.beans.property.StringProperty code;
    private final javafx.beans.property.StringProperty title;
    private final javafx.beans.property.StringProperty description;
    private final javafx.beans.property.StringProperty state;
    private final javafx.beans.property.StringProperty project;

    public Task() {
        code = new SimpleStringProperty(this, "code");
        title = new SimpleStringProperty(this, "tilte");
        description = new SimpleStringProperty(this, "description");
        state = new SimpleStringProperty(this, "state");
        project = new SimpleStringProperty(this, "project");
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

    public javafx.beans.property.StringProperty projectProperty() {return project; }
    public String getProject() {return project.getValue();}
    public void setProject(String project) {this.project.setValue(project);}
}
