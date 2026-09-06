package com.gym.gym_ff_backend.dto;

import com.gym.gym_ff_backend.entity.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponse {

    private Long id;
    private Long memberId;
    private Long membershipId;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String transactionId;
    private String status;

    public PaymentResponse(
            Long id,
            Long memberId,
            Long membershipId,
            BigDecimal amount,
            LocalDate paymentDate,
            String paymentMethod,
            String transactionId,
            String status) {

        this.id = id;
        this.memberId = memberId;
        this.membershipId = membershipId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getStatus() {
        return status;
    }
}