package com.example.gestionproyectos.clases;

import javafx.scene.control.Alert;

public class ALert
{
    public static void createErrorAlert(String type){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fail");
        alert.setHeaderText("PROJECT MANAGEMENT");
        alert.setContentText("Project " + type + " Failed");
        alert.showAndWait();
    }

    public static void createSuccesAlert(String type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("PROJECT MANAGEMENT");
        alert.setContentText("Project " + type + " Successfully");
        alert.showAndWait();
    }
}
