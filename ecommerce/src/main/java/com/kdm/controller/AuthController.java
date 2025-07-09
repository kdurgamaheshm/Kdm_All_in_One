package com.kdm.controller;

import com.kdm.domain.USER_ROLE;
import com.kdm.model.User;
import com.kdm.model.VerificationCode;
import com.kdm.repository.UserRepository;
import com.kdm.request.LoginOtpRequest;
import com.kdm.request.LoginRequest;
import com.kdm.response.ApiResponse;
import com.kdm.response.AuthResponse;
import com.kdm.response.SignUpRequest;
import com.kdm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignUpRequest req) throws Exception {
        String jwt= authService.createUser(req);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("registered successfully");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);



        return ResponseEntity.ok(res);
    }
    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> setOtpHandler(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sentLoginOtp(req.getEmail(),req.getRole());
        ApiResponse res=new ApiResponse();

        res.setMessage("Otp sent successfully");

        return ResponseEntity.ok(res);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest request) throws Exception {

        AuthResponse authResponse=   authService.signing(request);

        return ResponseEntity.ok(authResponse);
    }
}