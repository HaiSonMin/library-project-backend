package com.haison.libraryapplication.requestModels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentInfoRequest {
    private long amount;
    private String currency;
    private String receiptEmail;
}
