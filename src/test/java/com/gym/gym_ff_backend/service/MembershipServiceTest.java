package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.CreateMembershipRequest;
import com.gym.gym_ff_backend.dto.MembershipResponse;
import com.gym.gym_ff_backend.entity.Member;
import com.gym.gym_ff_backend.entity.Membership;
import com.gym.gym_ff_backend.entity.MembershipPlan;
import com.gym.gym_ff_backend.entity.MembershipStatus;
import com.gym.gym_ff_backend.repository.MemberRepository;
import com.gym.gym_ff_backend.repository.MembershipPlanRepository;
import com.gym.gym_ff_backend.repository.MembershipRepository;
import com.gym.gym_ff_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

class MembershipServiceTest {

    @Test
    void shouldUpdateMembershipStatuses() {

        Membership expiredMembership = new Membership();
        expiredMembership.setStartDate(
                LocalDate.of(2026, 8, 1));
        expiredMembership.setEndDate(
                LocalDate.of(2026, 8, 31));
        expiredMembership.setStatus(
                MembershipStatus.ACTIVE);

        Membership activeMembership = new Membership();
        activeMembership.setStartDate(
                LocalDate.of(2026, 9, 1));
        activeMembership.setEndDate(
                LocalDate.of(2026, 9, 30));
        activeMembership.setStatus(
                MembershipStatus.SCHEDULED);

        Membership scheduledMembership = new Membership();
        scheduledMembership.setStartDate(
                LocalDate.of(2026, 10, 1));
        scheduledMembership.setEndDate(
                LocalDate.of(2026, 10, 31));
        scheduledMembership.setStatus(
                MembershipStatus.SCHEDULED);

        Membership cancelledMembership = new Membership();
        cancelledMembership.setStartDate(
                LocalDate.of(2026, 8, 1));
        cancelledMembership.setEndDate(
                LocalDate.of(2026, 8, 31));
        cancelledMembership.setStatus(
                MembershipStatus.CANCELLED);

        MembershipRepository membershipRepository =
                mock(MembershipRepository.class);

        MemberRepository memberRepository =
                mock(MemberRepository.class);

        MembershipPlanRepository membershipPlanRepository =
                mock(MembershipPlanRepository.class);

        UserRepository userRepository =
                mock(UserRepository.class);

        when(membershipRepository.findAll())
                .thenReturn(List.of(
                        expiredMembership,
                        activeMembership,
                        scheduledMembership,
                        cancelledMembership
                ));

        MembershipService membershipService =
                new MembershipService(
                        membershipRepository,
                        memberRepository,
                        membershipPlanRepository,
                        userRepository
                );

        LocalDate testDate =
                LocalDate.of(2026, 9, 15);

        membershipService.updateMembershipStatuses(testDate);

        verify(membershipRepository)
                .saveAll(anyList());

        assert expiredMembership.getStatus()
                == MembershipStatus.EXPIRED;

        assert activeMembership.getStatus()
                == MembershipStatus.ACTIVE;

        assert scheduledMembership.getStatus()
                == MembershipStatus.SCHEDULED;

        assert cancelledMembership.getStatus()
                == MembershipStatus.CANCELLED;
    }

    @Test
    void shouldRejectInactiveMembershipPlan() {

        MembershipPlan inactivePlan = new MembershipPlan();
        inactivePlan.setActive(false);

        MembershipRepository membershipRepository =
                mock(MembershipRepository.class);

        MemberRepository memberRepository =
                mock(MemberRepository.class);

        MembershipPlanRepository membershipPlanRepository =
                mock(MembershipPlanRepository.class);

        UserRepository userRepository =
                mock(UserRepository.class);

        Member member = new Member();
        member.setId(2L);

        when(memberRepository.findById(2L))
                .thenReturn(java.util.Optional.of(member));

        when(membershipPlanRepository.findById(1L))
                .thenReturn(java.util.Optional.of(inactivePlan));

        MembershipService membershipService =
                new MembershipService(
                        membershipRepository,
                        memberRepository,
                        membershipPlanRepository,
                        userRepository
                );

        CreateMembershipRequest request =
                new CreateMembershipRequest();

        request.setMemberId(2L);
        request.setMembershipPlanId(1L);

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> membershipService.createMembership(request)
        );
    }

    @Test
    void shouldScheduleMembershipAfterExistingMembership() {

        Member member = new Member();
        member.setId(2L);

        MembershipPlan plan = new MembershipPlan();
        plan.setId(1L);
        plan.setDurationInDays(30);
        plan.setActive(true);

        Membership existingMembership = new Membership();
        existingMembership.setEndDate(
                LocalDate.of(2026, 9, 30));

        MembershipRepository membershipRepository =
                mock(MembershipRepository.class);

        MemberRepository memberRepository =
                mock(MemberRepository.class);

        MembershipPlanRepository membershipPlanRepository =
                mock(MembershipPlanRepository.class);

        UserRepository userRepository =
                mock(UserRepository.class);

        when(memberRepository.findById(2L))
                .thenReturn(java.util.Optional.of(member));

        when(membershipPlanRepository.findById(1L))
                .thenReturn(java.util.Optional.of(plan));

        when(membershipRepository
                .findByMemberIdAndEndDateGreaterThanEqualOrderByEndDateDesc(
                        2L,
                        LocalDate.now()))
                .thenReturn(List.of(existingMembership));

        when(membershipRepository.save(any(Membership.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MembershipService membershipService =
                new MembershipService(
                        membershipRepository,
                        memberRepository,
                        membershipPlanRepository,
                        userRepository
                );

        CreateMembershipRequest request =
                new CreateMembershipRequest();

        request.setMemberId(2L);
        request.setMembershipPlanId(1L);

        MembershipResponse response =
                membershipService.createMembership(request);

        assert response.getStartDate()
                .equals(LocalDate.of(2026, 10, 1));

        assert response.getEndDate()
                .equals(LocalDate.of(2026, 10, 30));

        assert response.getStatus()
                == MembershipStatus.SCHEDULED;
    }

    @Test
    void shouldCreateActiveMembershipWhenNoExistingMembership() {

        Member member = new Member();
        member.setId(2L);

        MembershipPlan plan = new MembershipPlan();
        plan.setId(1L);
        plan.setDurationInDays(30);
        plan.setActive(true);

        MembershipRepository membershipRepository =
                mock(MembershipRepository.class);

        MemberRepository memberRepository =
                mock(MemberRepository.class);

        MembershipPlanRepository membershipPlanRepository =
                mock(MembershipPlanRepository.class);

        UserRepository userRepository =
                mock(UserRepository.class);

        when(memberRepository.findById(2L))
                .thenReturn(java.util.Optional.of(member));

        when(membershipPlanRepository.findById(1L))
                .thenReturn(java.util.Optional.of(plan));

        when(membershipRepository
                .findByMemberIdAndEndDateGreaterThanEqualOrderByEndDateDesc(
                        2L,
                        LocalDate.now()))
                .thenReturn(List.of());

        when(membershipRepository.save(any(Membership.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        MembershipService membershipService =
                new MembershipService(
                        membershipRepository,
                        memberRepository,
                        membershipPlanRepository,
                        userRepository
                );

        CreateMembershipRequest request =
                new CreateMembershipRequest();

        request.setMemberId(2L);
        request.setMembershipPlanId(1L);

        MembershipResponse response =
                membershipService.createMembership(request);

        assert response.getPurchaseDate()
                .equals(LocalDate.now());

        assert response.getStartDate()
                .equals(LocalDate.now());

        assert response.getEndDate()
                .equals(LocalDate.now().plusDays(29));

        assert response.getStatus()
                == MembershipStatus.ACTIVE;
    }
}