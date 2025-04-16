package com.example.eventclubapi.controller;

import com.example.eventclubapi.model.Event;
import com.example.eventclubapi.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api2/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @PostMapping
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        Optional<Event> existingEvent = eventRepository.findByName(event.getName());

        if (existingEvent.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Event with the name '" + event.getName() + "' already exists.");
        }

        Event savedEvent = eventRepository.save(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        if (!eventRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        eventRepository.deleteById(id);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Optional<Event> existing = eventRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }

        event.setId(id);
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchEvent(@PathVariable Long id, @RequestBody Event patchData) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }

        Event event = optionalEvent.get();

        if (patchData.getName() != null) event.setName(patchData.getName());
        if (patchData.getDescription() != null) event.setDescription(patchData.getDescription());
        if (patchData.getStartDate() != null) event.setStartDate(patchData.getStartDate());
        if (patchData.getEndDate() != null) event.setEndDate(patchData.getEndDate());
        if (patchData.getLocation() != null) event.setLocation(patchData.getLocation());
        if (patchData.getCreatedBy() != null) event.setCreatedBy(patchData.getCreatedBy());
        if (patchData.getByClub() != null) event.setByClub(patchData.getByClub());
        if (patchData.getVolCount() != 0) event.setVolCount(patchData.getVolCount());
        if (patchData.getOrgCount() != 0) event.setOrgCount(patchData.getOrgCount());
        if (patchData.getMaxParticipants() != 0) event.setMaxParticipants(patchData.getMaxParticipants());
        if (patchData.getCurrParticipants() != 0) event.setCurrParticipants(patchData.getCurrParticipants());
        if (patchData.getBudget() != 0) event.setBudget(patchData.getBudget());
        if (patchData.getFirstContact() != null) event.setFirstContact(patchData.getFirstContact());
        if (patchData.getSecContact() != null) event.setSecContact(patchData.getSecContact());

        Event updated = eventRepository.save(event);
        return ResponseEntity.ok(updated);
    }
}
