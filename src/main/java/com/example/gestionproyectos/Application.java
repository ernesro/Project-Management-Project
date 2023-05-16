package com.example.gestionproyectos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("welcome-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("PROJECT MANAGEMENT");
        Image icon = new Image(getClass().getResourceAsStream("/imgs/icono.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Main method of the application
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}