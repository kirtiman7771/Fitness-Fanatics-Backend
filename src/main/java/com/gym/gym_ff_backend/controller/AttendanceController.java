package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.AttendanceResponse;
import com.gym.gym_ff_backend.service.AttendanceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @memberService.isCurrentUserOwner(#memberId))")
    @PostMapping("/check-in/{memberId}")
    public ResponseEntity<AttendanceResponse> checkIn(
            @PathVariable Long memberId) {

        AttendanceResponse attendance =
                attendanceService.checkIn(memberId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(attendance);
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @memberService.isCurrentUserOwner(#memberId))")
    @PutMapping("/check-out/{memberId}")
    public ResponseEntity<AttendanceResponse> checkOut(
            @PathVariable Long memberId) {

        AttendanceResponse attendance =
                attendanceService.checkOut(memberId);

        return ResponseEntity.ok(attendance);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AttendanceResponse>> getAllAttendance() {

        List<AttendanceResponse> attendance =
                attendanceService.getAllAttendance();

        return ResponseEntity.ok(attendance);
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @memberService.isCurrentUserOwner(#memberId))")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByMemberId(
            @PathVariable Long memberId) {

        List<AttendanceResponse> attendance =
                attendanceService.getAttendanceByMemberId(memberId);

        return ResponseEntity.ok(attendance);
    }
}