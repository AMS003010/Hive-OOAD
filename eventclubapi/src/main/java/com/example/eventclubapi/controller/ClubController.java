package com.example.eventclubapi.controller;

import com.example.eventclubapi.model.Club;
import com.example.eventclubapi.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api2/club")
public class ClubController {

    @Autowired
    private ClubRepository clubRepository;

    @PostMapping
    public ResponseEntity<?> addClub(@RequestBody Club club) {
        Optional<Club> existingClub = clubRepository.findByName(club.getName());

        if (existingClub.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Club with the name '" + club.getName() + "' already exists.");
        }

        Club savedClub = clubRepository.save(club);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClub);
    }

    @GetMapping
    public List<Club> getAllClubs() {
        return clubRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClub(@PathVariable Long id) {
        if (!clubRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Club not found");
        }
        clubRepository.deleteById(id);
        return ResponseEntity.ok("Club deleted successfully");
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> updateClub(@PathVariable Long id, @RequestBody Club club) {
        Optional<Club> existing = clubRepository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Club not found");
        }

        club.setId(id);
        return ResponseEntity.ok(clubRepository.save(club));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patchClub(@PathVariable Long id, @RequestBody Club patchData) {
        Optional<Club> optionalClub = clubRepository.findById(id);
        if (optionalClub.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Club not found");
        }

        Club club = optionalClub.get();

        if (patchData.getName() != null) club.setName(patchData.getName());
        if (patchData.getDescription() != null) club.setDescription(patchData.getDescription());
        if (patchData.getClubChair() != null) club.setClubChair(patchData.getClubChair());
        if (patchData.getClubCoordinator() != null) club.setClubCoordinator(patchData.getClubCoordinator());
        if (patchData.getEmail() != null) club.setEmail(patchData.getEmail());
        if (patchData.getMemCount() != 0) club.setMemCount(patchData.getMemCount());
        if (patchData.getCategory() != null) club.setCategory(patchData.getCategory());

        Club updated = clubRepository.save(club);
        return ResponseEntity.ok(updated);
    }
}
