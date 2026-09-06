package com.gym.gym_ff_backend.service;

import com.gym.gym_ff_backend.dto.PaymentResponse;
import com.gym.gym_ff_backend.repository.PaymentRepository;
import com.gym.gym_ff_backend.dto.CreatePaymentRequest;
import com.gym.gym_ff_backend.entity.Member;
import com.gym.gym_ff_backend.entity.Membership;
import com.gym.gym_ff_backend.exception.ResourceNotFoundException;
import com.gym.gym_ff_backend.repository.MemberRepository;
import com.gym.gym_ff_backend.repository.MembershipRepository;
import org.springframework.stereotype.Service;
import com.gym.gym_ff_backend.entity.MembershipStatus;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    public PaymentService(
            PaymentRepository paymentRepository,
            MemberRepository memberRepository,
            MembershipRepository membershipRepository) {

        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
    }

    public List<PaymentResponse> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(payment ->
                        new PaymentResponse(
                                payment.getId(),
                                payment.getMember().getId(),
                                payment.getMembership().getId(),
                                payment.getAmount(),
                                payment.getPaymentDate(),
                                payment.getPaymentMethod(),
                                payment.getTransactionId(),
                                payment.getStatus()
                        )
                )
                .toList();
    }
    public PaymentResponse createPayment(
            CreatePaymentRequest request) {

        Member member =
                memberRepository.findById(request.getMemberId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Member not found"));

        Membership membership =
                membershipRepository.findById(
                                request.getMembershipId())
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Membership not found"));

        if (!membership.getMember().getId()
                .equals(member.getId())) {

            throw new IllegalArgumentException(
                    "Membership does not belong to the member");
        }

        if (membership.getStatus() == MembershipStatus.EXPIRED
                || membership.getStatus() == MembershipStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Payment cannot be made for an expired or cancelled membership");
        }

        var payment = new com.gym.gym_ff_backend.entity.Payment();

        payment.setMember(member);
        payment.setMembership(membership);
        payment.setAmount(request.getAmount());
        payment.setPaymentDate(java.time.LocalDate.now());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setTransactionId(request.getTransactionId());
        payment.setStatus("SUCCESS");

        var savedPayment =
                paymentRepository.save(payment);

        return new PaymentResponse(
                savedPayment.getId(),
                savedPayment.getMember().getId(),
                savedPayment.getMembership().getId(),
                savedPayment.getAmount(),
                savedPayment.getPaymentDate(),
                savedPayment.getPaymentMethod(),
                savedPayment.getTransactionId(),
                savedPayment.getStatus()
        );
    }
    public List<PaymentResponse> getPaymentsByMemberId(Long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException(
                    "Member not found");
        }

        return paymentRepository.findByMemberId(memberId)
                .stream()
                .map(payment ->
                        new PaymentResponse(
                                payment.getId(),
                                payment.getMember().getId(),
                                payment.getMembership().getId(),
                                payment.getAmount(),
                                payment.getPaymentDate(),
                                payment.getPaymentMethod(),
                                payment.getTransactionId(),
                                payment.getStatus()
                        )
                )
                .toList();
    }
}