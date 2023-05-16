package com.example.gestionproyectos.clases;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void codeProperty() {
        Team team = new Team();
        team.setCode("ABC123");
        assertEquals("ABC123", team.getCode());
    }

    @Test
    void getCode() {
        Team team = new Team();
        team.setCode("XYZ987");
        assertEquals("XYZ987", team.getCode());
    }

    @Test
    void setCode() {
        Team team = new Team();
        team.setCode("ABC123");
        assertEquals("ABC123", team.getCode());
    }

    @Test
    void nameProperty() {
        Team team = new Team();
        team.setName("Team A");
        assertEquals("Team A", team.getName());
    }

    @Test
    void getName() {
        Team team = new Team();
        team.setName("Team B");
        assertEquals("Team B", team.getName());
    }

    @Test
    void setName() {
        Team team = new Team();
        team.setName("Team C");
        assertEquals("Team C", team.getName());
    }
}