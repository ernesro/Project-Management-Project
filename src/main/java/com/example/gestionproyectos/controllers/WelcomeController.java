package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.WindowFXML;
import com.example.gestionproyectos.data.dataBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeController implements Initializable
{
    @FXML
    private ImageView img;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image = new Image(getClass().getResourceAsStream("/imgs/mainImage.jpeg"));
        img.setImage(image);
        img.setX(75);
        img.fitHeightProperty();
        img.fitWidthProperty();
    }

    public void projectsBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("project-view.fxml", 1010, 600);
        window.showAndWait();
        dataBase.close();
    }

    public void tasksBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("tasks-view.fxml", 1038, 592);
        window.showAndWait();
        dataBase.close();
    }

    public void commentsBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("comment-view.fxml", 923, 598);
        window.showAndWait();
        dataBase.close();
    }

    public void teamBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("team-view.fxml", 971, 566);
        window.showAndWait();
        dataBase.close();
    }

    public void employeesBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("employee-view.fxml", 1070, 600);
        window.showAndWait();
        dataBase.close();
    }

    public void assignTeamBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("assignTeam-view.fxml", 992, 554);
        window.showAndWait();
        dataBase.close();
    }

    public void assignTaskBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("assignTask-view.fxml", 989, 596);
        window.showAndWait();
        dataBase.close();
    }

    public void assignEmployeeBt() throws IOException {
        WindowFXML window = new WindowFXML("assignEmployee-view.fxml", 989, 596);
        window.showAndWait();
        dataBase.close();
    }
}