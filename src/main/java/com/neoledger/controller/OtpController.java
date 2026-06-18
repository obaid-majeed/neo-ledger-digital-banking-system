package com.neoledger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neoledger.dto.OtpRequest;
import com.neoledger.dto.OtpVerifyRequest;
import com.neoledger.service.OtpService;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public String sendOtp(
            @RequestBody OtpRequest request) {

        otpService.generateOtp(
                request.getMobile());

        return "OTP Sent";
    }

    @PostMapping("/verify")
    public String verifyOtp(
            @RequestBody OtpVerifyRequest request) {

        boolean valid =
                otpService.verifyOtp(
                        request.getMobile(),
                        request.getOtp());

        return valid
                ? "OTP Verified"
                : "Invalid OTP";
    }
}