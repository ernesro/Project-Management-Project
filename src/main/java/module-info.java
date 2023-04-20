module com.example.gestionproyectos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;


    opens com.example.gestionproyectos to javafx.fxml;
    exports com.example.gestionproyectos;
    exports com.example.gestionproyectos.controllers;
    opens com.example.gestionproyectos.controllers to javafx.fxml;
}