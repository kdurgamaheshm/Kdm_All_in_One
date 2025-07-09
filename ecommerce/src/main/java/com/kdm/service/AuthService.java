package com.kdm.service;

import com.kdm.domain.USER_ROLE;
import com.kdm.request.LoginRequest;
import com.kdm.response.AuthResponse;
import com.kdm.response.SignUpRequest;
import jakarta.mail.MessagingException;

public interface AuthService {

    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignUpRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;
}
