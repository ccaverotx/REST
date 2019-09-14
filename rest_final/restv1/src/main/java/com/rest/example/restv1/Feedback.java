package com.rest.example.restv1;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "PASAJERO_FEEDBACK")
class Feedback {

    private @Id @GeneratedValue Long id;

    private String description;
    private Feed feed;

    Feedback() {}

    Feedback(String description, Feed feed) {

        this.description = description;
        this.feed = feed;
    }
}