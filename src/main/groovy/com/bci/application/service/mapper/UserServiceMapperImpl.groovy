package com.bci.application.service.mapper

import com.bci.domain.LoginResponse
import com.bci.domain.SignUpRequest
import com.bci.domain.SignUpResponse
import com.bci.domain.User
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
class UserServiceMapperImpl implements UserServiceMapper{
    @Override
    User toDomain(SignUpRequest request, String passwordEncrypt, String jwt) {
        return User.builder()
                .name(request.name)
                .email(request.email)
                .password(passwordEncrypt)
                .created(LocalDate.now())
                .phones(request.phones)
                .token(jwt)
                .isActive(true)
                .build()
    }

    @Override
    SignUpResponse toResponse(User user) {
        return SignUpResponse.builder()
                .id(user.id)
                .created(user.created)
                .lastLogin(user.lastLogin)
                .token(user.token)
                .isActive(user.isActive)
                .build()
    }

    @Override
    LoginResponse toLoginResponse(User user, String newJwt) {
        return LoginResponse.builder()
                .id(user.id)
                .created(user.created)
                .lastLogin(LocalDate.now())
                .token(newJwt)
                .isActive(user.isActive)
                .name(user.name)
                .email(user.email)
                .password(user.password)
                .phones(user.phones)
                .build()
    }
}
