package com.dog.care.dog_care.service;

import com.dog.care.dog_care.models.Dog;
import com.dog.care.dog_care.models.Family;
import com.dog.care.dog_care.models.Owner;
import com.dog.care.dog_care.repository.DogRepository;
import com.dog.care.dog_care.repository.FamilyRepository;
import com.dog.care.dog_care.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class OwnerService {


    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private DogRepository dogRepository;

    //Crear dueño
    public Owner crearDueno(Long familyId, Owner owner){

        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new RuntimeException("Family not found"));
        owner.setFamily(family);
        return ownerRepository.save(owner);


    }

    //Eliminar dueño
    public void eliminarDueno(Long id){
        ownerRepository.deleteById(id);
    }


    //Buscar dueño
    public Owner buscarDueno(Long id){
        return ownerRepository.findById(id).orElse(null);
    }

    //Actualizar dueño
    public Owner updateFamilyName(long id, String newName) {
        Optional<Owner> optionalOwner = ownerRepository.findById(id);//Optional es un contenedor que puede o no contener un valor no nulo
        if (optionalOwner.isPresent()) {
            Owner owner = optionalOwner.get();
            owner.setName(newName);
            return ownerRepository.save(owner);
        }
        return null;
    }

    public Dog registrarPaseo(Long ownerId, Long dogId) {
        // Buscar si el owner y el dog existen
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        Dog dog = dogRepository.findById(dogId).orElse(null);

        if (owner != null && dog != null) {
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Obtener solo la parte de la fecha (sin la hora) para comparar
            String today = sdf.format(now);
            String lastWalkDay = dog.getLastWalkDate() != null ? sdf.format(dog.getLastWalkDate()) : null;

            // Si es un nuevo día, reiniciar el contador de paseos diarios
            if (!today.equals(lastWalkDay)) {
                dog.setPaseosDiarios(0);
            }

            // Incrementar los paseos del perro
            dog.setPaseosDiarios(dog.getPaseosDiarios() + 1);
            // Actualizar fecha del paseo
            dog.setLastWalkDate(now);

            // Guardar los cambios
            dogRepository.save(dog);
        }

        return dog;
    }


    //Metodo para registrar baño del perro
    public Dog registrarBano(Long ownerId, Long dogId) {
        // Buscar si el owner y el dog existen
        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        Dog dog = dogRepository.findById(dogId).orElse(null);

        if (owner != null && dog != null) {
            // Incrementar la cantidad de baños del perro
            dog.setCantidadBanos(dog.getCantidadBanos() + 1);

            // Crear un objeto Date con la fecha y hora actuales
            Date currentDate = new Date();

            // Guardar la fecha del baño
            dog.setBathDate(currentDate);

            // Guardar los cambios
            dogRepository.save(dog);
        }

        return dog;
    }

    //Método para obtener las actividades de un dueño y sus perros
    public List<Map<String, Object>> obtenerActividadesDeOwner(Long ownerId) {
        List<Map<String, Object>> actividades = new ArrayList<>();

        Owner owner = ownerRepository.findById(ownerId).orElse(null);
        if (owner != null) {
            Family family = owner.getFamily();
            if (family != null) {
                List<Dog> dogs = family.getDogs();

                for (Dog dog : dogs) {
                    Map<String, Object> actividad = new HashMap<>();
                    actividad.put("ownerName", owner.getName());
                    actividad.put("dogName", dog.getName());
                    actividad.put("totalPaseos", dog.getPaseosDiarios());
                    actividad.put("totalBanos", dog.getCantidadBanos());
                    actividad.put("lastBathDate", dog.getBathDate());
                    actividad.put("lastWalkDate", dog.getLastWalkDate());

                    actividades.add(actividad);
                }
            } else {
                System.out.println("No se encontró la familia");
            }
        } else {
            System.out.println("No se encontró el dueño");
        }

        return actividades;
    }





}
