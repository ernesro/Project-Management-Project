package com.example.gestionproyectos;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowFXML extends Stage
{
    public WindowFXML(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fileName));
        Parent root = loader.load();
        setTitle("PROJECT MANAGEMENT");
        Scene scene = new Scene(root, 1100, 600);
        setScene(scene);
    }
}
