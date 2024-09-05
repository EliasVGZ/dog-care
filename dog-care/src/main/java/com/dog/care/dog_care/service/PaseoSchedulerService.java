package com.dog.care.dog_care.service;

import com.dog.care.dog_care.models.Dog;
import com.dog.care.dog_care.repository.DogRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PaseoSchedulerService {

    private final DogRepository dogRepository;

    public PaseoSchedulerService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    // Programar la tarea para que se ejecute todos los días a las 00:00
    @Scheduled(cron = "0 0 0 * * ?")
    public void resetPaseosDiarios() {
        List<Dog> dogs = dogRepository.findAll();
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(now);

        for (Dog dog : dogs) {
            String lastWalkDay = dog.getLastWalkDate() != null ? sdf.format(dog.getLastWalkDate()) : null;

            // Si es un nuevo día, reiniciar el contador de paseos diarios
            if (!today.equals(lastWalkDay)) {
                dog.setPaseosDiarios(0);
                dogRepository.save(dog); // Guardar los cambios en la base de datos
            }
        }
    }
}

