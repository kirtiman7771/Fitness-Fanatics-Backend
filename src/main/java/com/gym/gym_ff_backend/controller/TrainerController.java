package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.CreateTrainerRequest;
import com.gym.gym_ff_backend.dto.TrainerResponse;
import com.gym.gym_ff_backend.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.gym.gym_ff_backend.dto.UpdateTrainerRequest;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'MEMBER')")
    public ResponseEntity<List<TrainerResponse>> getAllTrainers() {

        return ResponseEntity.ok(
                trainerService.getAllTrainers()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'MEMBER')")
    public ResponseEntity<TrainerResponse> getTrainerById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                trainerService.getTrainerById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrainerResponse> createTrainer(
            @Valid @RequestBody CreateTrainerRequest request) {

        TrainerResponse createdTrainer =
                trainerService.createTrainer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdTrainer);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TrainerResponse> updateTrainer(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTrainerRequest request) {

        TrainerResponse updatedTrainer =
                trainerService.updateTrainer(id, request);

        return ResponseEntity.ok(updatedTrainer);
    }
}