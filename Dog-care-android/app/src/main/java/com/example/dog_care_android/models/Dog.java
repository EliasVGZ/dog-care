package com.example.dog_care_android.models;

import java.time.LocalDate;
import java.util.Date;

public class Dog {


    private long id;
    private String name;
    private String birthDate;
    private int paseosDiarios;
    private Date bathDate;
    private int cantidadBanos;
    private Date lastWalkDate;

    public Date getLastWalkDate() {
        return lastWalkDate;
    }

    public void setLastWalkDate(Date lastWalkDate) {
        this.lastWalkDate = lastWalkDate;
    }

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

    public Date getBathDate() {
        return bathDate;
    }

    public void setBathDate(Date bathDate) {
        this.bathDate = bathDate;
    }

    public int getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(int cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
    }
}
