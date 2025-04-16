package com.example.eventclubapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "organiser")
@Data@NoArgsConstructor
@AllArgsConstructor
public class Organiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phno;
    private String event;
}
