package com.rest.example.restv1;

class PasajeroNotFoundException extends RuntimeException {

    PasajeroNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}