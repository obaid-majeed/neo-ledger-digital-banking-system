package com.neoledger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neoledger.service.OtpService;

@RestController
@RequestMapping("/api/transfer-otp")
@CrossOrigin("*")
public class TransferOtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public String sendOtp(
            @RequestParam String mobile) {

        otpService.generateOtp(mobile);

        return "OTP Sent";
    }

    @PostMapping("/verify")
    public boolean verifyOtp(
            @RequestParam String mobile,
            @RequestParam String otp) {

        return otpService.verifyOtp(
                mobile,
                otp);
    }
}
