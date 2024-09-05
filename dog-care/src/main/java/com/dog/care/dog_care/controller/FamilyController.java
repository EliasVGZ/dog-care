package com.dog.care.dog_care.controller;

import com.dog.care.dog_care.models.Family;
import com.dog.care.dog_care.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/family")
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @PostMapping
    public ResponseEntity<Family> createFamily(@RequestBody Family family) {
        Family createdFamily = familyService.createFamily(family);
        return ResponseEntity.ok(createdFamily);
    }

    //Listar todos los integrantes de una familia y sus perros
    @GetMapping("/{id}")
    public ResponseEntity<Family> getFamilyById(@PathVariable("id") long id){
        Family family = familyService.getFamilyById(id);
        if(family == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(family);
    }

    //Actualizar el nombre de una familia
    @PutMapping("/{id}")
    public ResponseEntity<Family> updateFamilyName(@PathVariable("id") long id, @RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        if (name == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Family family = familyService.updateFamilyName(id, name);
        if(family == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(family);
    }






}
