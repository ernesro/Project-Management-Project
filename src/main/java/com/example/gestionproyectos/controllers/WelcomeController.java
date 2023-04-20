package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.WindowFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WelcomeController
{
    @FXML
    Button projBt;
    @FXML
    Button tasksBt;

    public void projectsBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("project-view.fxml");
        window.show();
    }

    public void tasksBt_Click(ActionEvent actionEvent) throws IOException {
        WindowFXML window = new WindowFXML("tasks-view.fxml");
        window.show();
    }
}