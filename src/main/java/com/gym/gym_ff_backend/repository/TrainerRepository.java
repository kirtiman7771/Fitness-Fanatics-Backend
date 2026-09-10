package com.gym.gym_ff_backend.repository;

import com.gym.gym_ff_backend.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}