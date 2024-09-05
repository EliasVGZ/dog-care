package com.dog.care.dog_care.service;

import com.dog.care.dog_care.models.Family;
import com.dog.care.dog_care.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FamilyService {

    @Autowired
    private FamilyRepository familyRepository;


    //Crear familia
    public Family createFamily(Family family){
        return familyRepository.save(family);
    }

    //Listar de una familia todos sus integrandes y sus perros
    public Family getFamilyById(long id){
        return familyRepository.findById(id).orElse(null);
    }

    //Actualizar nombre de la familia
    public Family updateFamilyName(long id, String newName) {
        Optional<Family> optionalFamily = familyRepository.findById(id);//Optional es un contenedor que puede o no contener un valor no nulo
        if (optionalFamily.isPresent()) {
            Family family = optionalFamily.get();
            family.setName(newName);
            return familyRepository.save(family);
        }
        return null;
    }

}
