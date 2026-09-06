package com.gym.gym_ff_backend.dto;

import com.gym.gym_ff_backend.entity.AttendanceStatus;

import java.time.LocalDateTime;

public class AttendanceResponse {

    private Long id;
    private Long memberId;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private AttendanceStatus status;

    public AttendanceResponse(
            Long id,
            Long memberId,
            LocalDateTime checkInTime,
            LocalDateTime checkOutTime,
            AttendanceStatus status) {

        this.id = id;
        this.memberId = memberId;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public AttendanceStatus getStatus() {
        return status;
    }
}