package com.example.agentic_ai_for_crud.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

enum UserRole {
    ADMIN, GENERAL, GUEST
}

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.GENERAL;

    private String location;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private double balance;

}
