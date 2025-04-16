package com.example.eventclubapi.repository;

import com.example.eventclubapi.model.Organiser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganiserRepository extends JpaRepository<Organiser, Long> {
    Optional<Organiser> findByName(String name);
}
