package com.dog.care.dog_care.repository;

import com.dog.care.dog_care.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findByFamilyId(Long familyId);

}
