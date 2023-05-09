package com.haison.libraryapplication.service.impl;

import com.haison.libraryapplication.entity.Payment;
import com.haison.libraryapplication.repository.PaymentRepository;
import com.haison.libraryapplication.requestModels.PaymentInfoRequest;
import com.haison.libraryapplication.service.PaymentService;
import com.okta.spring.boot.oauth.Okta;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@PropertySource("classpath:application.properties")
public class PaymentServiceImpl implements PaymentService{

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              @Value("${stripe.key.secret}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfoRequest paymentInfoRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount",paymentInfoRequest.getAmount());
        params.put("currency",paymentInfoRequest.getCurrency());
        params.put("payment_method_types",paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    @Override
    public ResponseEntity<String> stripePayment(String userEmail) throws Exception {
        Payment payment = this.paymentRepository.findByUserEmail(userEmail);

        if(payment == null) throw new Exception("Payment information is missing");

        payment.setAmount(00.00);
        this.paymentRepository.save(payment);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
