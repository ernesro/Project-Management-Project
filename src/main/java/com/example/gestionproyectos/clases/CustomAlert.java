package com.example.gestionproyectos.clases;

import com.example.gestionproyectos.controllers.AssignEmployeeController;
import javafx.scene.control.Alert;
/**
 * Class that creates custom alerts
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class CustomAlert extends AssignEmployeeController {
    /**
     * Method that creates an Error Alert
     * @param type string that represents the type of alert
     * @param className string that represents the name of the class
     */
    public static void createErrorAlert(String type, String className){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fail");
        alert.setHeaderText(className + " MANAGEMENT");
        alert.setContentText(className + type + " Failed");
        alert.showAndWait();
    }
    /**
     * Method that creates a Success Alert
     * @param type string that represents the type of alert
     * @param className string that represents the name of the class
     */
    public static void createSuccesAlert(String type, String className){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(className + " MANAGEMENT");
        alert.setContentText(className + type + " Successfully");
        alert.showAndWait();
    }
}
