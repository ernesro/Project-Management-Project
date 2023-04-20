package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.WindowFXML;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class WelcomeController
{
    @FXML
    Button projBt;

    public void projectsBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("project-view.fxml");
        window.show();
    }
}