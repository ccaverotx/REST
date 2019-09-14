package com.rest.example.restv1;

class TripNotFoundException extends RuntimeException {

    TripNotFoundException(Long id) {
        super("Could not find trip " + id);
    }
}