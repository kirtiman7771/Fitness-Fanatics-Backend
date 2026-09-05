package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.CreateMembershipRequest;
import com.gym.gym_ff_backend.dto.MembershipResponse;
import com.gym.gym_ff_backend.entity.Member;
import com.gym.gym_ff_backend.entity.Membership;
import com.gym.gym_ff_backend.entity.MembershipPlan;
import com.gym.gym_ff_backend.entity.MembershipStatus;
import com.gym.gym_ff_backend.exception.ResourceNotFoundException;
import com.gym.gym_ff_backend.repository.MemberRepository;
import com.gym.gym_ff_backend.repository.MembershipPlanRepository;
import com.gym.gym_ff_backend.repository.MembershipRepository;
import com.gym.gym_ff_backend.entity.User;
import com.gym.gym_ff_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;
    private final MembershipPlanRepository membershipPlanRepository;
    private final UserRepository userRepository;

    public MembershipService(
            MembershipRepository membershipRepository,
            MemberRepository memberRepository,
            MembershipPlanRepository membershipPlanRepository,
            UserRepository userRepository) {

        this.membershipRepository = membershipRepository;
        this.memberRepository = memberRepository;
        this.membershipPlanRepository = membershipPlanRepository;
        this.userRepository = userRepository;
    }

    public List<MembershipResponse> getAllMemberships() {

        return membershipRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MembershipResponse getMembershipById(Long id) {

        Membership membership =
                membershipRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership not found"));

        return mapToResponse(membership);
    }
    public List<MembershipResponse> getMembershipsByMemberId(Long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member not found");
        }

        return membershipRepository.findByMemberId(memberId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    public boolean isCurrentUserOwner(Long membershipId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        return user.getMember() != null
                && membershipRepository.findById(membershipId)
                .map(membership ->
                        membership.getMember().getId()
                                .equals(user.getMember().getId()))
                .orElse(false);
    }
    public MembershipResponse createMembership(
            CreateMembershipRequest request) {

        Member member =
                memberRepository.findById(request.getMemberId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"));

        MembershipPlan membershipPlan =
                membershipPlanRepository
                        .findById(request.getMembershipPlanId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership plan not found"));
        if (!membershipPlan.getActive()) {
            throw new IllegalStateException(
                    "Membership plan is not active");
        }
        LocalDate purchaseDate = LocalDate.now();

        LocalDate startDate = purchaseDate;

        MembershipStatus status = MembershipStatus.ACTIVE;

        List<Membership> existingMemberships =
                membershipRepository
                        .findByMemberIdAndEndDateGreaterThanEqualOrderByEndDateDesc(
                                member.getId(),
                                purchaseDate);

        if (!existingMemberships.isEmpty()) {

            Membership latestMembership =
                    existingMemberships.get(0);

            startDate =
                    latestMembership.getEndDate().plusDays(1);

            status = MembershipStatus.SCHEDULED;
        }

        Membership membership = new Membership();

        membership.setMember(member);
        membership.setMembershipPlan(membershipPlan);
        membership.setPurchaseDate(purchaseDate);
        membership.setStartDate(startDate);
        membership.setEndDate(
                startDate.plusDays(
                        membershipPlan.getDurationInDays() - 1));
        membership.setStatus(status);

        Membership savedMembership =
                membershipRepository.save(membership);

        return mapToResponse(savedMembership);
    }

    public void updateMembershipStatuses() {

        updateMembershipStatuses(LocalDate.now());
    }
    public MembershipResponse cancelMembership(Long id) {

        Membership membership =
                membershipRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership not found"));

        if (membership.getStatus() == MembershipStatus.CANCELLED) {
            throw new IllegalStateException(
                    "Membership is already cancelled");
        }

        if (membership.getStatus() == MembershipStatus.EXPIRED) {
            throw new IllegalStateException(
                    "Expired membership cannot be cancelled");
        }

        membership.setStatus(MembershipStatus.CANCELLED);

        Membership updatedMembership =
                membershipRepository.save(membership);

        return mapToResponse(updatedMembership);
    }
    public void updateMembershipStatuses(LocalDate today) {

        List<Membership> memberships =
                membershipRepository.findAll();

        for (Membership membership : memberships) {

            if (membership.getStatus() == MembershipStatus.CANCELLED) {
                continue;
            }

            if (membership.getEndDate().isBefore(today)) {
                membership.setStatus(MembershipStatus.EXPIRED);
            }
            else if (!membership.getStartDate().isAfter(today)) {
                membership.setStatus(MembershipStatus.ACTIVE);
            }
            else {
                membership.setStatus(MembershipStatus.SCHEDULED);
            }
        }

        membershipRepository.saveAll(memberships);
    }

    private MembershipResponse mapToResponse(
            Membership membership) {

        return new MembershipResponse(
                membership.getId(),
                membership.getMember().getId(),
                membership.getMembershipPlan().getId(),
                membership.getPurchaseDate(),
                membership.getStartDate(),
                membership.getEndDate(),
                membership.getStatus()
        );
    }
}