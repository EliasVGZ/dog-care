package com.dog.care.dog_care.controller;

import com.dog.care.dog_care.models.Dog;
import com.dog.care.dog_care.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dog")
public class DogController {

    @Autowired
    private DogService dogService;

    //Buscar perro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Dog> buscarPerro(@PathVariable Long id) {
        Dog dog = dogService.buscarPerro(id);
        return ResponseEntity.ok(dog);
    }


    //TODO Crear perro en una familia
    @PostMapping(value = "/family/{familyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Dog> createDog(@PathVariable Long familyId, @RequestBody Dog dog) {
        Dog createdDog = dogService.crearPerro(familyId, dog);
        return ResponseEntity.ok(createdDog);
    }




    //TODO Borrar perro
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void borrarPerro(@PathVariable Long id) {
        dogService.borrarPerro(id);
    }






}
