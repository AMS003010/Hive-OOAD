package com.example.eventclubapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "club")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String clubChair;
    private String clubCoordinator;
    private String email;
    private int memCount;
    private String category;
}
