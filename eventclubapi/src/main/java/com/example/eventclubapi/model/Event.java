package com.example.eventclubapi.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String location;
    private String createdBy;
    private String byClub;

    private int volCount;
    private int orgCount;
    private int maxParticipants;
    private int currParticipants;

    private double budget;

    private String firstContact;
    private String secContact;
}
