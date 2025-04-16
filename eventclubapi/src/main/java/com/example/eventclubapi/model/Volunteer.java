package com.example.eventclubapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "volunteer")
@Data@NoArgsConstructor
@AllArgsConstructor
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phno;
    private String event;
}
