package com.bank.payment.service;

import com.bank.payment.model.PaymentRequest;
import com.bank.payment.model.PaymentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    /**
     * Process payment transfer
     */
    public PaymentResponse processPayment(PaymentRequest request) {
        log.info("Processing payment | from={} to={} amount={}",
                request.getFromAccount(), request.getToAccount(), request.getAmount());

        // Generate unique transaction ID
        String transactionId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Business logic for payment processing
        PaymentResponse response = new PaymentResponse();
        response.setTransactionId(transactionId);
        response.setFromAccount(request.getFromAccount());
        response.setToAccount(request.getToAccount());
        response.setAmount(request.getAmount());
        response.setCurrency(request.getCurrency() != null ? request.getCurrency() : "INR");
        response.setTimestamp(LocalDateTime.now());
        response.setStatus("SUCCESS");
        response.setMessage("Payment processed successfully");

        log.info("Payment SUCCESS | transactionId={}", transactionId);
        return response;
    }

    /**
     * Get payment status
     */
    public PaymentResponse getPaymentStatus(String transactionId) {
        log.info("Fetching payment status | transactionId={}", transactionId);

        PaymentResponse response = new PaymentResponse();
        response.setTransactionId(transactionId);
        response.setStatus("SUCCESS");
        response.setTimestamp(LocalDateTime.now());
        response.setMessage("Transaction found");

        return response;
    }
}
