package com.neoledger.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.neoledger.service.OtpService;

@Service
public class OtpServiceImpl
        implements OtpService {

    private final Map<String,String>
            otpStore =
            new HashMap<>();

    @Override
    public String generateOtp(
            String mobile) {

        String otp =
                String.valueOf(
                        100000 +
                        new Random()
                        .nextInt(900000));

        otpStore.put(
                mobile,
                otp);

        System.out.println(
                "OTP FOR "
                + mobile
                + " = "
                + otp);

        return otp;
    }

    @Override
    public boolean verifyOtp(
            String mobile,
            String otp) {

        String savedOtp =
                otpStore.get(
                        mobile);

        return savedOtp != null
                &&
                savedOtp.equals(
                        otp);
    }
}