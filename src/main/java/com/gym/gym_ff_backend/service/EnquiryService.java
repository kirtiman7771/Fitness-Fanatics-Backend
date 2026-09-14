package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.CreateEnquiryRequest;
import com.gym.gym_ff_backend.dto.EnquiryResponse;
import com.gym.gym_ff_backend.dto.UpdateEnquiryStatusRequest;
import com.gym.gym_ff_backend.entity.Enquiry;
import com.gym.gym_ff_backend.entity.EnquiryStatus;
import com.gym.gym_ff_backend.exception.ResourceNotFoundException;
import com.gym.gym_ff_backend.repository.EnquiryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnquiryService {

    private final EnquiryRepository enquiryRepository;

    public EnquiryService(EnquiryRepository enquiryRepository) {
        this.enquiryRepository = enquiryRepository;
    }

    public EnquiryResponse createEnquiry(
            CreateEnquiryRequest request) {

        Enquiry enquiry = new Enquiry();

        enquiry.setFirstName(request.getFirstName());
        enquiry.setLastName(request.getLastName());
        enquiry.setPhone(request.getPhone());
        enquiry.setEmail(request.getEmail());
        enquiry.setMessage(request.getMessage());

        enquiry.setEnquiryDate(LocalDateTime.now());
        enquiry.setStatus(EnquiryStatus.NEW);

        Enquiry savedEnquiry =
                enquiryRepository.save(enquiry);

        return mapToResponse(savedEnquiry);
    }

    public List<EnquiryResponse> getAllEnquiries() {

        return enquiryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public EnquiryResponse getEnquiryById(Long id) {

        Enquiry enquiry = enquiryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enquiry not found"));

        return mapToResponse(enquiry);
    }

    public EnquiryResponse updateEnquiryStatus(
            Long id,
            UpdateEnquiryStatusRequest request) {

        Enquiry enquiry = enquiryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Enquiry not found"));

        enquiry.setStatus(request.getStatus());

        Enquiry updatedEnquiry =
                enquiryRepository.save(enquiry);

        return mapToResponse(updatedEnquiry);
    }

    private EnquiryResponse mapToResponse(
            Enquiry enquiry) {

        return new EnquiryResponse(
                enquiry.getId(),
                enquiry.getFirstName(),
                enquiry.getLastName(),
                enquiry.getPhone(),
                enquiry.getEmail(),
                enquiry.getMessage(),
                enquiry.getEnquiryDate(),
                enquiry.getStatus()
        );
    }
}