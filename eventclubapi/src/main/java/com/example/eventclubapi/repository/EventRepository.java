package com.example.eventclubapi.repository;

import com.example.eventclubapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String name);
}
