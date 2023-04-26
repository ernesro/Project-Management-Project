package com.example.gestionproyectos.clases;

import javafx.beans.value.ObservableValue;

public class Employee
{
    private final javafx.beans.property.StringProperty dni;
    private final javafx.beans.property.StringProperty name;
    private final javafx.beans.property.StringProperty lastname;
    private final javafx.beans.property.StringProperty email;
    private final javafx.beans.property.StringProperty phone;
    private final javafx.beans.property.StringProperty address;

    public Employee() {
        dni = new javafx.beans.property.SimpleStringProperty(this, "dni");
        name = new javafx.beans.property.SimpleStringProperty(this, "name");
        lastname = new javafx.beans.property.SimpleStringProperty(this, "lastname");
        email = new javafx.beans.property.SimpleStringProperty(this, "email");
        phone = new javafx.beans.property.SimpleStringProperty(this, "phone");
        address = new javafx.beans.property.SimpleStringProperty(this, "address");
    }

    public javafx.beans.property.StringProperty dniProperty(){return dni;}
    public String getDni(){return dni.getValue();}
    public void setDni(String c){dni.setValue(c);}

    public javafx.beans.property.StringProperty nameProperty(){return name;}
    public String getName(){return name.getValue();}
    public void setName(String c){name.setValue(c);}

    public javafx.beans.property.StringProperty lastnameProperty(){return lastname;}
    public String getLastname(){return lastname.getValue();}
    public void setLastName(String c){lastname.setValue(c);}

    public javafx.beans.property.StringProperty emailProperty(){return email;}
    public String getEmail(){return email.getValue();}
    public void setEmail(String c){email.setValue(c);}

    public javafx.beans.property.StringProperty phoneProperty(){return phone;}
    public String getPhone(){return phone.getValue();}
    public void setPhone(String c){phone.setValue(c);}

    public javafx.beans.property.StringProperty addressProperty(){return address;}
    public String getAddress(){return address.getValue();}
    public void setAddress(String c){address.setValue(c);}

}
