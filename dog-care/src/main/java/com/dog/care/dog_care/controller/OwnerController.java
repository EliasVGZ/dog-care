package com.dog.care.dog_care.controller;

import com.dog.care.dog_care.models.Dog;
import com.dog.care.dog_care.models.Owner;
import com.dog.care.dog_care.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    
    @Autowired
    private OwnerService ownerService;
    
    
    
    //Crear Dueno
    @PostMapping("/family/{familyId}")
    public ResponseEntity<Owner> createOwner(@PathVariable Long familyId, @RequestBody Owner owner) {
        Owner createdOwner = ownerService.crearDueno(familyId, owner);
        return ResponseEntity.ok(createdOwner);
    }

    
    //Eliminar dueno psandole id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarDueno(@PathVariable Long id) {
        ownerService.eliminarDueno(id);
    }
    
    //Buscar dueno pasandole id
    @GetMapping("/{id}")
    public Owner buscarDueno(@PathVariable Long id) {
        return ownerService.buscarDueno(id);
    }
    
    //Actualizar dueno
    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwnerName(@PathVariable("id") long id, @RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        if (name == null) {
            return ResponseEntity.badRequest().body(null);
        }

        Owner owner = ownerService.updateFamilyName(id, name);
        if(owner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owner);
    }

    //Sumar +1 cuando algun dueño hizo un paseo con su perro
    //Metodo para registrar un paseo
    @PostMapping("/{ownerId}/dog/{dogId}/paseo")
    public ResponseEntity<Dog> registrarPaseo(@PathVariable Long ownerId, @PathVariable Long dogId) {
        Dog updatedDog = ownerService.registrarPaseo(ownerId, dogId);
        if (updatedDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDog);
    }

    // Método para registrar un baño realizado por un Owner
    @PostMapping("/{ownerId}/dog/{dogId}/bano")
    public ResponseEntity<Dog> registrarBano(@PathVariable Long ownerId, @PathVariable Long dogId) {
        Dog updatedDog = ownerService.registrarBano(ownerId, dogId);
        if (updatedDog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedDog);
    }

    // Nuevo endpoint para obtener las actividades de un dueño y su(s) perro(s)
    @GetMapping("/{ownerId}/actividades")
    public ResponseEntity<List<Map<String, Object>>> obtenerActividadesDeOwner(@PathVariable Long ownerId) {

        System.out.println("Solicitando actividades para ownerId: " + ownerId);

        List<Map<String, Object>> actividades = ownerService.obtenerActividadesDeOwner(ownerId);
        if (actividades.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actividades);
    }










}
