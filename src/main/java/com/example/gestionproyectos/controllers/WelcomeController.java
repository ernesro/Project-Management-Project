package com.example.gestionproyectos.controllers;

import com.example.gestionproyectos.Application;
import com.example.gestionproyectos.WindowFXML;
import com.example.gestionproyectos.data.dataBase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/**
 * Class that controls the welcome window
 * @version 2.0
 * @Author Ernestas Urbonas
 */
public class WelcomeController implements Initializable
{
    /**
     * Method that initializes the window
     * @param url
     * @param resourceBundle
     */
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

    /**
     * Method that creates a project window from a FXML file
     * @throws IOException
     */
    public void projectsBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("project-view.fxml", 1010, 600);
        window.showAndWait();
        dataBase.close();
    }
    /**
     * Method that creates a task window from a FXML file
     * @throws IOException
     */
    public void tasksBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("tasks-view.fxml", 1038, 592);
        window.showAndWait();
        dataBase.close();
    }
    /**
     * Method that creates a comment window from a FXML file
     * @throws IOException
     */
    public void commentsBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("comment-view.fxml", 923, 598);
        window.showAndWait();
        dataBase.close();
    }
    /**
     * Method that creates a team window from a FXML file
     * @throws IOException
     */
    public void teamBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("team-view.fxml", 971, 566);
        window.showAndWait();
        dataBase.close();
    }
    /**
     * Method that creates an employee window from a FXML file
     * @throws IOException
     */
    public void employeesBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("employee-view.fxml", 1070, 600);
        window.showAndWait();
        dataBase.close();
    }
    /**
     * Method that creates an assign team window from a FXML file
     * @throws IOException
     */
    public void assignTeamBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("assignTeam-view.fxml", 992, 554);
        window.showAndWait();
        dataBase.close();
    }
    /**
     * Method that creates an assign task window from a FXML file
     * @throws IOException
     */
    public void assignTaskBt_Click() throws IOException {
        WindowFXML window = new WindowFXML("assignTask-view.fxml", 989, 596);
        window.showAndWait();
        dataBase.close();
    }
    /**
     * Method that creates an assign employee window from a FXML file
     * @throws IOException
     */
    public void assignEmployeeBt() throws IOException {
        WindowFXML window = new WindowFXML("assignEmployee-view.fxml", 989, 596);
        window.showAndWait();
        dataBase.close();
    }
}