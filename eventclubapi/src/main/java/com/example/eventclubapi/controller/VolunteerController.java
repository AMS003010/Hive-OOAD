package com.example.eventclubapi.controller;

import com.example.eventclubapi.model.Volunteer;
import com.example.eventclubapi.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api2/volunteer")
public class VolunteerController {
    @Autowired
    private VolunteerRepository volunteerRepository;

    @PostMapping
    public ResponseEntity<?> addVolunteer(@RequestBody Volunteer volunteer) {
        Optional<Volunteer> existingVolunteer = volunteerRepository.findByName(volunteer.getName());

        if (existingVolunteer.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Volunteer with the name '" + volunteer.getName() + "' already exists.");
        }

        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVolunteer);
    }

    @GetMapping
    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVolunteer(@PathVariable Long id) {
        if (!volunteerRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Volunteer not found");
        }
        volunteerRepository.deleteById(id);
        return ResponseEntity.ok("Volunteer deleted successfully");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateVolunteer(@PathVariable Long id, @RequestBody Volunteer volunteer) {
        Optional<Volunteer> existing = volunteerRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Volunteer not found");
        }

        volunteer.setId(id);
        return ResponseEntity.ok(volunteerRepository.save(volunteer));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchClub(@PathVariable Long id, @RequestBody Volunteer patchData) {
        Optional<Volunteer> optionalVolunteer = volunteerRepository.findById(id);
        if (optionalVolunteer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Volunteer not found");
        }

        Volunteer volunteer = optionalVolunteer.get();

        if (patchData.getName() != null) volunteer.setName(patchData.getName());
        if (patchData.getEmail() != null) volunteer.setEmail(patchData.getEmail());
        if (patchData.getPhno() != null) volunteer.setPhno(patchData.getPhno());
        if (patchData.getEvent() != null) volunteer.setEvent(patchData.getEvent());

        Volunteer updated = volunteerRepository.save(volunteer);
        return ResponseEntity.ok(updated);
    }
}
