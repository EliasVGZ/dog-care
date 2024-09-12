package com.example.dog_care_android.models;

import java.time.LocalDate;
import java.util.Date;

public class Dog {


    private long id;
    private String name;
    private String birthDate;
    private int paseosDiarios;
    private String bathDate;
    private int cantidadBanos;
    private String lastWalkDate;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPaseosDiarios() {
        return paseosDiarios;
    }

    public void setPaseosDiarios(int paseosDiarios) {
        this.paseosDiarios = paseosDiarios;
    }


    public int getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(int cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }

    public String getBathDate() {
        return bathDate;
    }

    public void setBathDate(String bathDate) {
        this.bathDate = bathDate;
    }

    public String getLastWalkDate() {
        return lastWalkDate;
    }

    public void setLastWalkDate(String lastWalkDate) {
        this.lastWalkDate = lastWalkDate;
    }


}
