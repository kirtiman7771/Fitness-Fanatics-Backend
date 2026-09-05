package com.gym.gym_ff_backend.repository;

import com.gym.gym_ff_backend.entity.Membership;
import com.gym.gym_ff_backend.entity.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MembershipRepository
        extends JpaRepository<Membership, Long> {

    List<Membership> findByMemberId(Long memberId);

    List<Membership> findByMemberIdAndStatus(
            Long memberId,
            MembershipStatus status);

    List<Membership> findByMemberIdAndStatusOrderByEndDateDesc(
            Long memberId,
            MembershipStatus status);

    List<Membership> findByMemberIdAndEndDateGreaterThanEqualOrderByEndDateDesc(
            Long memberId,
            LocalDate date);
}