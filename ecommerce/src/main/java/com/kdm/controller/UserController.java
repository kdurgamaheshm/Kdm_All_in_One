package com.kdm.controller;

import com.kdm.domain.USER_ROLE;
import com.kdm.model.User;
import com.kdm.response.AuthResponse;
import com.kdm.response.SignUpRequest;
import com.kdm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandler(
            @RequestHeader ("Authorization") String jwt) throws Exception {

        User user =userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);

    }

}
