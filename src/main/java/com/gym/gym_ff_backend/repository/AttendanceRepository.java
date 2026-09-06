package com.gym.gym_ff_backend.repository;

import com.gym.gym_ff_backend.entity.Attendance;
import com.gym.gym_ff_backend.entity.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByMemberIdAndStatus(
            Long memberId,
            AttendanceStatus status
    );

    Optional<Attendance> findFirstByMemberIdAndStatusOrderByCheckInTimeDesc(
            Long memberId,
            AttendanceStatus status
    );
    List<Attendance> findByMemberIdOrderByCheckInTimeDesc(
            Long memberId
    );

}