package com.gym.gym_ff_backend.repository;

import com.gym.gym_ff_backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}