package com.gym.gym_ff_backend.controller;

import com.gym.gym_ff_backend.dto.CreatePaymentRequest;
import com.gym.gym_ff_backend.dto.PaymentResponse;
import com.gym.gym_ff_backend.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PaymentResponse>> getAllPayments() {

        return ResponseEntity.ok(
                paymentService.getAllPayments()
        );
    }
    @PreAuthorize("hasRole('ADMIN') or (hasRole('MEMBER') and @memberService.isCurrentUserOwner(#memberId))")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByMemberId(
            @PathVariable Long memberId) {

        return ResponseEntity.ok(
                paymentService.getPaymentsByMemberId(memberId)
        );
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody CreatePaymentRequest request) {

        PaymentResponse createdPayment =
                paymentService.createPayment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPayment);
    }
}