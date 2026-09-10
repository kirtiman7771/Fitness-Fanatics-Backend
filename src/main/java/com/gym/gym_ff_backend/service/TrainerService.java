package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.CreateTrainerRequest;
import com.gym.gym_ff_backend.dto.TrainerResponse;
import com.gym.gym_ff_backend.entity.Trainer;
import com.gym.gym_ff_backend.exception.ResourceNotFoundException;
import com.gym.gym_ff_backend.repository.TrainerRepository;
import org.springframework.stereotype.Service;
import com.gym.gym_ff_backend.dto.UpdateTrainerRequest;

import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<TrainerResponse> getAllTrainers() {

        return trainerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TrainerResponse getTrainerById(Long id) {

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer not found"));

        return mapToResponse(trainer);
    }

    public TrainerResponse createTrainer(
            CreateTrainerRequest request) {

        Trainer trainer = new Trainer();

        trainer.setFirstName(request.getFirstName());
        trainer.setLastName(request.getLastName());
        trainer.setEmail(request.getEmail());
        trainer.setPhone(request.getPhone());
        trainer.setSpecialization(request.getSpecialization());
        trainer.setExperience(request.getExperience());
        trainer.setBio(request.getBio());

        trainer.setActive(true);

        Trainer savedTrainer =
                trainerRepository.save(trainer);

        return mapToResponse(savedTrainer);
    }

    private TrainerResponse mapToResponse(
            Trainer trainer) {

        return new TrainerResponse(
                trainer.getId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getEmail(),
                trainer.getPhone(),
                trainer.getSpecialization(),
                trainer.getExperience(),
                trainer.getBio(),
                trainer.getActive()
        );
    }
    public TrainerResponse updateTrainer(
            Long id,
            UpdateTrainerRequest request) {

        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Trainer not found"));

        trainer.setFirstName(request.getFirstName());
        trainer.setLastName(request.getLastName());
        trainer.setEmail(request.getEmail());
        trainer.setPhone(request.getPhone());
        trainer.setSpecialization(request.getSpecialization());
        trainer.setExperience(request.getExperience());
        trainer.setBio(request.getBio());

        if (request.getActive() != null) {
            trainer.setActive(request.getActive());
        }

        Trainer updatedTrainer =
                trainerRepository.save(trainer);

        return mapToResponse(updatedTrainer);
    }
}