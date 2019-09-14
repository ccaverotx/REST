package com.rest.example.restv1;

import org.springframework.data.jpa.repository.JpaRepository;

interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
