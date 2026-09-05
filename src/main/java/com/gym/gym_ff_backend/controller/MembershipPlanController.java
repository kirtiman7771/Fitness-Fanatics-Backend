package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.CreateMembershipPlanRequest;
import com.gym.gym_ff_backend.dto.MembershipPlanResponse;
import com.gym.gym_ff_backend.dto.UpdateMembershipPlanRequest;
import com.gym.gym_ff_backend.service.MembershipPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership-plans")
public class MembershipPlanController {

    private final MembershipPlanService membershipPlanService;

    public MembershipPlanController(
            MembershipPlanService membershipPlanService) {

        this.membershipPlanService = membershipPlanService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipPlanResponse>> getAllPlans() {

        return ResponseEntity.ok(
                membershipPlanService.getAllPlans()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipPlanResponse> getPlanById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                membershipPlanService.getPlanById(id)
        );
    }

    @PostMapping
    public ResponseEntity<MembershipPlanResponse> createPlan(
            @Valid @RequestBody CreateMembershipPlanRequest request) {

        MembershipPlanResponse createdPlan =
                membershipPlanService.createPlan(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPlan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipPlanResponse> updatePlan(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMembershipPlanRequest request) {

        return ResponseEntity.ok(
                membershipPlanService.updatePlan(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(
            @PathVariable Long id) {

        membershipPlanService.deletePlan(id);

        return ResponseEntity.noContent().build();
    }
}