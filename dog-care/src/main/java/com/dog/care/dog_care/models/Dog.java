package com.dog.care.dog_care.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date birthDate;
    private int paseosDiarios;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date bathDate;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date lastWalkDate;
    private int cantidadBanos;
    @ManyToOne
    @JoinColumn(name = "family_id")  // Columna para la referencia a la familia
    @JsonIgnore
    private Family family;

    public Dog() {
    }

    public Date getLastWalkDate() {
        return lastWalkDate;
    }

    public void setLastWalkDate(Date lastWalkDate) {
        this.lastWalkDate = lastWalkDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getPaseosDiarios() {
        return paseosDiarios;
    }

    public int getCantidadBanos() {
        return cantidadBanos;
    }

    public void setCantidadBanos(int cantidadBanos) {
        this.cantidadBanos = cantidadBanos;
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

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }
}
