package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.CreateMembershipRequest;
import com.gym.gym_ff_backend.dto.MembershipResponse;
import com.gym.gym_ff_backend.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(
            MembershipService membershipService) {

        this.membershipService = membershipService;
    }

    @GetMapping
    public ResponseEntity<List<MembershipResponse>> getAllMemberships() {

        return ResponseEntity.ok(
                membershipService.getAllMemberships()
        );
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @membershipService.isCurrentUserOwner(#id))")
    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponse> getMembershipById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                membershipService.getMembershipById(id)
        );
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @memberService.isCurrentUserOwner(#memberId))")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MembershipResponse>> getMembershipsByMemberId(
            @PathVariable Long memberId) {

        return ResponseEntity.ok(
                membershipService.getMembershipsByMemberId(memberId)
        );
    }
    @PostMapping
    public ResponseEntity<MembershipResponse> createMembership(
            @Valid @RequestBody CreateMembershipRequest request) {

        MembershipResponse createdMembership =
                membershipService.createMembership(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMembership);
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @membershipService.isCurrentUserOwner(#id))")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<MembershipResponse> cancelMembership(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                membershipService.cancelMembership(id)
        );
    }
}