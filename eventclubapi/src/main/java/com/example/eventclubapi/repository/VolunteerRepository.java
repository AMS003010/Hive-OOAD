package com.example.eventclubapi.repository;

import com.example.eventclubapi.model.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Optional<Volunteer> findByName(String name);
}
