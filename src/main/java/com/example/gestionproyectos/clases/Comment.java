package com.example.gestionproyectos.clases;

import javafx.beans.property.SimpleStringProperty;

public class Comment
{
    private final javafx.beans.property.StringProperty code;
    private final javafx.beans.property.StringProperty content;
    private final javafx.beans.property.StringProperty cod_task;

    public Comment() {
        code = new SimpleStringProperty(this, "code");
        content = new SimpleStringProperty(this, "content");
        cod_task = new SimpleStringProperty(this, "cod_task");
    }

    public javafx.beans.property.StringProperty codeProperty(){return code;}
    public String getCode(){return code.getValue();}
    public void setCode(String c){code.setValue(c);}

    public javafx.beans.property.StringProperty contentProperty(){return content;}
    public String getContent(){return content.getValue();}
    public void setContent(String c){content.setValue(c);}

    public javafx.beans.property.StringProperty cod_taskProperty(){return cod_task;}
    public String getCod_task(){return cod_task.getValue();}
    public void setCod_task(String c){cod_task.setValue(c);}
}
