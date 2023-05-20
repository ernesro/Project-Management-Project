package com.example.gestionproyectos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * JavaFX App
 * Principal class of the application
 * @version 2.0
 * @author Ernestas Urbonas
 */
public class Application extends javafx.application.Application {
    /**
     * Method that starts the application
     * @param stage Stage of the application
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        WindowFXML window = new WindowFXML("login-view.fxml", 351, 400);
        window.showAndWait();
    }
    /**
     * Main method of the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}