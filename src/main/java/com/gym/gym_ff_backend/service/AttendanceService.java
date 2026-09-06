package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.AttendanceResponse;
import com.gym.gym_ff_backend.entity.Attendance;
import com.gym.gym_ff_backend.entity.AttendanceStatus;
import com.gym.gym_ff_backend.entity.Member;
import com.gym.gym_ff_backend.entity.MembershipStatus;
import com.gym.gym_ff_backend.exception.ResourceNotFoundException;
import com.gym.gym_ff_backend.repository.AttendanceRepository;
import com.gym.gym_ff_backend.repository.MemberRepository;
import com.gym.gym_ff_backend.repository.MembershipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            MemberRepository memberRepository,
            MembershipRepository membershipRepository) {

        this.attendanceRepository = attendanceRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
    }

    public AttendanceResponse checkIn(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found"));

        if (!Boolean.TRUE.equals(member.getActive())) {
            throw new IllegalStateException(
                    "Inactive member cannot check in");
        }

        var activeMemberships =
                membershipRepository.findByMemberIdAndStatus(
                        memberId,
                        MembershipStatus.ACTIVE
                );

        if (activeMemberships.isEmpty()) {
            throw new IllegalStateException(
                    "Member does not have an active membership");
        }

        if (attendanceRepository.findByMemberIdAndStatus(
                memberId,
                AttendanceStatus.CHECKED_IN
        ).isPresent()) {

            throw new IllegalStateException(
                    "Member is already checked in");
        }

        Attendance attendance = new Attendance();

        attendance.setMember(member);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.CHECKED_IN);

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return mapToResponse(savedAttendance);
    }

    public AttendanceResponse checkOut(Long memberId) {

        Attendance attendance =
                attendanceRepository
                        .findFirstByMemberIdAndStatusOrderByCheckInTimeDesc(
                                memberId,
                                AttendanceStatus.CHECKED_IN
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Member is not currently checked in"));

        attendance.setCheckOutTime(LocalDateTime.now());
        attendance.setStatus(AttendanceStatus.CHECKED_OUT);

        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        return mapToResponse(savedAttendance);
    }

    private AttendanceResponse mapToResponse(
            Attendance attendance) {

        return new AttendanceResponse(
                attendance.getId(),
                attendance.getMember().getId(),
                attendance.getCheckInTime(),
                attendance.getCheckOutTime(),
                attendance.getStatus()
        );
    }
    public List<AttendanceResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public List<AttendanceResponse> getAttendanceByMemberId(Long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found");
        }

        return attendanceRepository
                .findByMemberIdOrderByCheckInTimeDesc(memberId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
}