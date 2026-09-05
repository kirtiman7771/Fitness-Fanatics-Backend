package com.gym.gym_ff_backend.dto;

import com.gym.gym_ff_backend.entity.MembershipStatus;

import java.time.LocalDate;

public class MembershipResponse {

    private Long id;
    private Long memberId;
    private Long membershipPlanId;
    private LocalDate purchaseDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private MembershipStatus status;

    public MembershipResponse() {
    }

    public MembershipResponse(
            Long id,
            Long memberId,
            Long membershipPlanId,
            LocalDate purchaseDate,
            LocalDate startDate,
            LocalDate endDate,
            MembershipStatus status) {

        this.id = id;
        this.memberId = memberId;
        this.membershipPlanId = membershipPlanId;
        this.purchaseDate = purchaseDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getMembershipPlanId() {
        return membershipPlanId;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public MembershipStatus getStatus() {
        return status;
    }
}