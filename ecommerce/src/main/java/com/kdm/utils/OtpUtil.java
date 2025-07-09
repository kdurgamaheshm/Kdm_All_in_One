package com.kdm.utils;

import java.util.Random;

public class OtpUtil {
    public static String generateOtp() {
        int otpLength = 6;

        Random rand = new Random();
        StringBuilder otp=new StringBuilder();
        for(int i = 0; i < otpLength; i++){
            otp.append(rand.nextInt(10));
        }
        return otp.toString();
    }
}
