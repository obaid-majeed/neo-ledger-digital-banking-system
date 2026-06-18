package com.neoledger.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerifyRequest {

    private String mobile;

    private String otp;
}
