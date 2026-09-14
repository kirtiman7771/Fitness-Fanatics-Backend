package com.gym.gym_ff_backend.dto;

import com.gym.gym_ff_backend.entity.EnquiryStatus;

import java.time.LocalDateTime;

public class EnquiryResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String message;
    private LocalDateTime enquiryDate;
    private EnquiryStatus status;

    public EnquiryResponse(
            Long id,
            String firstName,
            String lastName,
            String phone,
            String email,
            String message,
            LocalDateTime enquiryDate,
            EnquiryStatus status) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.message = message;
        this.enquiryDate = enquiryDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getEnquiryDate() {
        return enquiryDate;
    }

    public EnquiryStatus getStatus() {
        return status;
    }
}