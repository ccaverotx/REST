package com.rest.example.restv1;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Pasajero {

    private @Id @GeneratedValue Long id;
    private String name;
    private String role;

    Pasajero() {}

    Pasajero(String name, String role) {
        this.name = name;
        this.role = role;
    }
}