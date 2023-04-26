package com.example.gestionproyectos.clases;

import javafx.scene.control.Alert;

public class ALert
{
    public static void createErrorAlert(String type, String className){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fail");
        alert.setHeaderText(className + "MANAGEMENT");
        alert.setContentText(className + type + " Failed");
        alert.showAndWait();
    }

    public static void createSuccesAlert(String type, String className){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(className + "MANAGEMENT");
        alert.setContentText(className + type + " Successfully");
        alert.showAndWait();
    }
}
