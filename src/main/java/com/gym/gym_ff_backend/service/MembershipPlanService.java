package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.CreateMembershipPlanRequest;
import com.gym.gym_ff_backend.dto.MembershipPlanResponse;
import com.gym.gym_ff_backend.dto.UpdateMembershipPlanRequest;
import com.gym.gym_ff_backend.entity.MembershipPlan;
import com.gym.gym_ff_backend.exception.ResourceNotFoundException;
import com.gym.gym_ff_backend.repository.MembershipPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipPlanService {

    private final MembershipPlanRepository membershipPlanRepository;

    public MembershipPlanService(MembershipPlanRepository membershipPlanRepository) {
        this.membershipPlanRepository = membershipPlanRepository;
    }

    public List<MembershipPlanResponse> getAllPlans() {

        return membershipPlanRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MembershipPlanResponse getPlanById(Long id) {

        MembershipPlan plan = membershipPlanRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Membership plan not found"));

        return mapToResponse(plan);
    }

    public MembershipPlanResponse createPlan(
            CreateMembershipPlanRequest request) {

        MembershipPlan plan = new MembershipPlan();

        plan.setName(request.getName());
        plan.setPrice(request.getPrice());
        plan.setDurationInDays(request.getDurationInDays());
        plan.setDescription(request.getDescription());
        plan.setActive(request.getActive());

        MembershipPlan savedPlan =
                membershipPlanRepository.save(plan);

        return mapToResponse(savedPlan);
    }

    public MembershipPlanResponse updatePlan(
            Long id,
            UpdateMembershipPlanRequest request) {

        MembershipPlan existingPlan =
                membershipPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership plan not found"));

        existingPlan.setName(request.getName());
        existingPlan.setPrice(request.getPrice());
        existingPlan.setDurationInDays(request.getDurationInDays());
        existingPlan.setDescription(request.getDescription());
        existingPlan.setActive(request.getActive());

        MembershipPlan updatedPlan =
                membershipPlanRepository.save(existingPlan);

        return mapToResponse(updatedPlan);
    }

    public void deletePlan(Long id) {

        MembershipPlan existingPlan =
                membershipPlanRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership plan not found"));

        membershipPlanRepository.delete(existingPlan);
    }

    private MembershipPlanResponse mapToResponse(
            MembershipPlan plan) {

        return new MembershipPlanResponse(
                plan.getId(),
                plan.getName(),
                plan.getPrice(),
                plan.getDurationInDays(),
                plan.getDescription(),
                plan.getActive()
        );
    }
}