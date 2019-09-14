package com.rest.example.restv1;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "PASAJERO_TRIP")
class Trip {

    private @Id @GeneratedValue Long id;

    private String description;
    private Status status;

    Trip() {}

    Trip(String description, Status status) {

        this.description = description;
        this.status = status;
    }
}
