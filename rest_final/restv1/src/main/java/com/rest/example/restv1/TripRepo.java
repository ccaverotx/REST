package com.rest.example.restv1;

import org.springframework.data.jpa.repository.JpaRepository;

interface TripRepo extends JpaRepository<Trip, Long> {

}
