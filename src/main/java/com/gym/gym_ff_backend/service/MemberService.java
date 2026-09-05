package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.CreateMemberRequest;
import com.gym.gym_ff_backend.dto.MemberResponse;
import com.gym.gym_ff_backend.dto.UpdateMemberRequest;
import com.gym.gym_ff_backend.entity.Member;
import com.gym.gym_ff_backend.exception.ResourceNotFoundException;
import com.gym.gym_ff_backend.repository.MemberRepository;
import com.gym.gym_ff_backend.entity.User;
import com.gym.gym_ff_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public MemberService(
            MemberRepository memberRepository,
            UserRepository userRepository) {

        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MemberResponse getMemberById(Long id) {

        Member member = memberRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Member not found"));

        return mapToResponse(member);
    }

    public MemberResponse createMember(
            CreateMemberRequest request) {

        Member member = new Member();

        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setEmail(request.getEmail());
        member.setPhone(request.getPhone());
        member.setDateOfBirth(request.getDateOfBirth());
        member.setGender(request.getGender());
        member.setAddress(request.getAddress());
        member.setJoinDate(request.getJoinDate());
        member.setActive(request.getActive());

        Member savedMember =
                memberRepository.save(member);

        return mapToResponse(savedMember);
    }

    private MemberResponse mapToResponse(Member member) {

        return new MemberResponse(
                member.getId(),
                member.getFirstName(),
                member.getLastName(),
                member.getEmail(),
                member.getPhone(),
                member.getDateOfBirth(),
                member.getGender(),
                member.getAddress(),
                member.getJoinDate(),
                member.getActive()
        );
    }
    public MemberResponse updateMember(
            Long id,
            UpdateMemberRequest request) {

        Member existingMember =
                memberRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"));

        existingMember.setFirstName(request.getFirstName());
        existingMember.setLastName(request.getLastName());
        existingMember.setEmail(request.getEmail());
        existingMember.setPhone(request.getPhone());
        existingMember.setDateOfBirth(request.getDateOfBirth());
        existingMember.setGender(request.getGender());
        existingMember.setAddress(request.getAddress());
        existingMember.setJoinDate(request.getJoinDate());
        existingMember.setActive(request.getActive());

        Member updatedMember =
                memberRepository.save(existingMember);

        return mapToResponse(updatedMember);
    }

    public void deleteMember(Long id) {

        Member existingMember =
                memberRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"));

        memberRepository.delete(existingMember);
    }
    public boolean isCurrentUserOwner(Long memberId) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        return user.getMember() != null
                && user.getMember().getId().equals(memberId);
    }
}