package com.bci.service.mapper;

import com.bci.domain.dto.LoginResponse;
import com.bci.domain.dto.SignUpRequest;
import com.bci.domain.dto.SignUpResponse;
import com.bci.domain.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserServiceMapperImpl implements UserServiceMapper {

    @Override
    public User toDomain(SignUpRequest request, String passwordEncrypt, String jwt) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncrypt)
                .created(LocalDate.now())
                .phones(request.getPhones())
                .token(jwt)
                .isActive(true)
                .build();
    }

    @Override
    public SignUpResponse toResponse(User user) {
        return SignUpResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(user.getLastLogin())
                .token(user.getToken())
                .isActive(user.isActive())
                .build();
    }

    @Override
    public LoginResponse toLoginResponse(User user, String newJwt) {
        return LoginResponse.builder()
                .id(user.getId())
                .created(user.getCreated())
                .lastLogin(LocalDate.now())
                .token(newJwt)
                .isActive(user.isActive())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phones(user.getPhones())
                .build();
    }
}