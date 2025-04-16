package com.example.eventclubapi.controller;

import com.example.eventclubapi.model.Organiser;
import com.example.eventclubapi.repository.OrganiserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api2/organiser")
public class OrganiserController {
    @Autowired
    private OrganiserRepository organiserRepository;

    @PostMapping
    public ResponseEntity<?> addOrganiser(@RequestBody Organiser organiser) {
        Optional<Organiser> existingOrganiser = organiserRepository.findByName(organiser.getName());

        if (existingOrganiser.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Organiser with the name '" + organiser.getName() + "' already exists.");
        }

        Organiser savedOrganiser = organiserRepository.save(organiser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrganiser);
    }

    @GetMapping
    public List<Organiser> getAllOrganisers() {
        return organiserRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrganiser(@PathVariable Long id) {
        if (!organiserRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organiser not found");
        }
        organiserRepository.deleteById(id);
        return ResponseEntity.ok("Organiser deleted successfully");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateOrganiser(@PathVariable Long id, @RequestBody Organiser organiser) {
        Optional<Organiser> existing = organiserRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organiser not found");
        }

        organiser.setId(id);
        return ResponseEntity.ok(organiserRepository.save(organiser));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchClub(@PathVariable Long id, @RequestBody Organiser patchData) {
        Optional<Organiser> optionalOrganiser = organiserRepository.findById(id);
        if (optionalOrganiser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Organiser not found");
        }

        Organiser organiser = optionalOrganiser.get();

        if (patchData.getName() != null) organiser.setName(patchData.getName());
        if (patchData.getEmail() != null) organiser.setEmail(patchData.getEmail());
        if (patchData.getPhno() != null) organiser.setPhno(patchData.getPhno());
        if (patchData.getEvent() != null) organiser.setEvent(patchData.getEvent());

        Organiser updated = organiserRepository.save(organiser);
        return ResponseEntity.ok(updated);
    }
}
