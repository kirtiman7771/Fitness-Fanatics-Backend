package com.gym.gym_ff_backend.dto;

import com.gym.gym_ff_backend.entity.EnquiryStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateEnquiryStatusRequest {

    @NotNull(message = "Status is required")
    private EnquiryStatus status;

    public UpdateEnquiryStatusRequest() {
    }

    public EnquiryStatus getStatus() {
        return status;
    }

    public void setStatus(EnquiryStatus status) {
        this.status = status;
    }
}