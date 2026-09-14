package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.CreateEnquiryRequest;
import com.gym.gym_ff_backend.dto.EnquiryResponse;
import com.gym.gym_ff_backend.dto.UpdateEnquiryStatusRequest;
import com.gym.gym_ff_backend.service.EnquiryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enquiries")
public class EnquiryController {

    private final EnquiryService enquiryService;

    public EnquiryController(EnquiryService enquiryService) {
        this.enquiryService = enquiryService;
    }

    @PostMapping
    public ResponseEntity<EnquiryResponse> createEnquiry(
            @Valid @RequestBody CreateEnquiryRequest request) {

        EnquiryResponse createdEnquiry =
                enquiryService.createEnquiry(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdEnquiry);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EnquiryResponse>> getAllEnquiries() {

        return ResponseEntity.ok(
                enquiryService.getAllEnquiries()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnquiryResponse> getEnquiryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                enquiryService.getEnquiryById(id)
        );
    }
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EnquiryResponse> updateEnquiryStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEnquiryStatusRequest request) {

        EnquiryResponse updatedEnquiry =
                enquiryService.updateEnquiryStatus(id, request);

        return ResponseEntity.ok(updatedEnquiry);
    }
}