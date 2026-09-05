package com.gym.gym_ff_backend.repository;

import com.gym.gym_ff_backend.entity.MembershipPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {
}