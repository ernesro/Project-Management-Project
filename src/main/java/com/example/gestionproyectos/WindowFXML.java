package com.example.gestionproyectos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowFXML extends Stage
{
    public WindowFXML(String fileName, double width, double height) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        Parent root = loader.load();
        setTitle("PROJECT MANAGEMENT");
        Scene scene = new Scene(root, width, height);
        Image icon = new Image(getClass().getResourceAsStream("/imgs/icono.png"));
        getIcons().add(icon);
        setResizable(false);
        setScene(scene);
    }
}
