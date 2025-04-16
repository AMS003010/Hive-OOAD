package com.example.eventclubapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "participant")
@Data@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phno;
    private String registration_status;
    private String event;
}
