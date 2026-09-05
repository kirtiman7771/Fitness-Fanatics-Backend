package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.CreateMemberRequest;
import com.gym.gym_ff_backend.dto.MemberResponse;
import com.gym.gym_ff_backend.dto.UpdateMemberRequest;
import com.gym.gym_ff_backend.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<MemberResponse>> getAllMembers() {
        return ResponseEntity.ok(
                memberService.getAllMembers()
        );
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @memberService.isCurrentUserOwner(#id))")
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> getMemberById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                memberService.getMemberById(id)
        );
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberResponse> createMember(
            @Valid @RequestBody CreateMemberRequest request) {

        MemberResponse createdMember =
                memberService.createMember(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdMember);
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @memberService.isCurrentUserOwner(#id))")
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMemberRequest request) {

        return ResponseEntity.ok(
                memberService.updateMember(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMember(
            @PathVariable Long id) {

        memberService.deleteMember(id);

        return ResponseEntity.noContent().build();
    }
}