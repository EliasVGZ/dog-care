package com.dog.care.dog_care.service;

import com.dog.care.dog_care.models.Dog;
import com.dog.care.dog_care.repository.DogRepository;
import com.dog.care.dog_care.repository.FamilyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {


    @Autowired
    private  DogRepository dogRepository;
    @Autowired
    private FamilyRepository familyRepository;

    //Buscar perro por su id
    public Dog buscarPerro(Long id){
        return dogRepository.findById(id).orElse(null);
    }

    //Crear perro
    public Dog crearPerro(Long familia_id, Dog perro){

        perro.setFamily(familyRepository.findById(familia_id).get());

        return dogRepository.save(perro);
    }


    //Listar perros
    public List<Dog> allDogs(){
        return dogRepository.findAll();
    }

    //Buscar perro


    //Borrar perro
    public void borrarPerro(Long id){
        dogRepository.deleteById(id);
    }















}
