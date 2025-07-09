package com.kdm.service.impl;

import com.kdm.config.JwtProvider;
import com.kdm.model.User;
import com.kdm.repository.UserRepository;
import com.kdm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email=jwtProvider.getEmailFromJwtToken(jwt);
        return this.findUserByEmail(email);
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=this.userRepository.findByEmail(email);
        if(user==null){
            throw new Exception("User not found with email :- "+email);
        }
        return user;
    }
}
