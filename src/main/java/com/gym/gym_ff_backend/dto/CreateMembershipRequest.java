package com.gym.gym_ff_backend.dto;

import jakarta.validation.constraints.NotNull;

public class CreateMembershipRequest {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Membership plan ID is required")
    private Long membershipPlanId;

    public CreateMembershipRequest() {
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMembershipPlanId() {
        return membershipPlanId;
    }

    public void setMembershipPlanId(Long membershipPlanId) {
        this.membershipPlanId = membershipPlanId;
    }
}