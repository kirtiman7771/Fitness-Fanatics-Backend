package com.gym.gym_ff_backend.repository;

import com.gym.gym_ff_backend.entity.Enquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {
}