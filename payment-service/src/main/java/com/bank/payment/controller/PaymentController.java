package com.bank.payment.controller;

import com.bank.payment.model.PaymentRequest;
import com.bank.payment.model.PaymentResponse;
import com.bank.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * PaymentController — REST API for Banking Payment Operations
 * Handles fund transfers, payment status checks
 */
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private PaymentService paymentService;

    /**
     * Initiate a fund transfer
     * POST /api/v1/payments/transfer
     */
    @PostMapping("/transfer")
    public ResponseEntity<PaymentResponse> initiateTransfer(@RequestBody PaymentRequest request) {
        log.info("Payment transfer initiated | from={} to={} amount={}",
                request.getFromAccount(), request.getToAccount(), request.getAmount());

        PaymentResponse response = paymentService.processPayment(request);

        log.info("Payment completed | transactionId={} status={}",
                response.getTransactionId(), response.getStatus());

        return ResponseEntity.ok(response);
    }

    /**
     * Get payment status by transaction ID
     * GET /api/v1/payments/status/{transactionId}
     */
    @GetMapping("/status/{transactionId}")
    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable String transactionId) {
        log.info("Payment status check | transactionId={}", transactionId);
        PaymentResponse response = paymentService.getPaymentStatus(transactionId);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint (used by K8s liveness probe)
     * GET /api/v1/payments/health
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service is UP");
    }
}
