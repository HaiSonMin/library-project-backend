package com.haison.libraryapplication.controller;

import com.haison.libraryapplication.requestModels.PaymentInfoRequest;
import com.haison.libraryapplication.service.PaymentService;
import com.haison.libraryapplication.utils.ExtractJWT;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://localhost:3000")
@RestController
@RequestMapping("command/payments/secure")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfoRequest paymentInfoRequest)
            throws StripeException {
        System.out.println(paymentInfoRequest.toString());
        PaymentIntent paymentIntent = this.paymentService.createPaymentIntent(paymentInfoRequest);

        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PutMapping("payment-complete")
    public ResponseEntity<String> stripePayment(@RequestHeader("Authorization") String token) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token,"\"sub\"");

        System.out.println(userEmail);

        if(userEmail == null) throw new Exception("User email is missing");

        return this.paymentService.stripePayment(userEmail);
    }

}
