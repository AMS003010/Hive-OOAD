package com.example.eventclubapi.controller;

import com.example.eventclubapi.model.Participant;
import com.example.eventclubapi.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api2/participant")
public class ParticipantController {
    @Autowired
    private ParticipantRepository participantRepository;

    @PostMapping
    public ResponseEntity<?> addParticipant(@RequestBody Participant participant) {
        Optional<Participant> existingParticipant = participantRepository.findByName(participant.getName());

        if (existingParticipant.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Participant with the name '" + participant.getName() + "' already exists.");
        }

        Participant savedParticipant = participantRepository.save(participant);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipant);
    }

    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteParticipant(@PathVariable Long id) {
        if (!participantRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
        }
        participantRepository.deleteById(id);
        return ResponseEntity.ok("Participant deleted successfully");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateParticipant(@PathVariable Long id, @RequestBody Participant participant) {
        Optional<Participant> existing = participantRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
        }

        participant.setId(id);
        return ResponseEntity.ok(participantRepository.save(participant));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchClub(@PathVariable Long id, @RequestBody Participant patchData) {
        Optional<Participant> optionalParticipant = participantRepository.findById(id);
        if (optionalParticipant.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
        }

        Participant participant = optionalParticipant.get();

        if (patchData.getName() != null) participant.setName(patchData.getName());
        if (patchData.getEmail() != null) participant.setEmail(patchData.getEmail());
        if (patchData.getPhno() != null) participant.setPhno(patchData.getPhno());
        if (patchData.getRegistration_status() != null) participant.setRegistration_status(patchData.getRegistration_status());
        if (patchData.getEvent() != null) participant.setEvent(patchData.getEvent());

        Participant updated = participantRepository.save(participant);
        return ResponseEntity.ok(updated);
    }
}
