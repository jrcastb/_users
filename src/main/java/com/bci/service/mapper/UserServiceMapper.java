package com.bci.service.mapper;

import com.bci.domain.dto.LoginResponse;
import com.bci.domain.dto.SignUpRequest;
import com.bci.domain.dto.SignUpResponse;
import com.bci.domain.User;

public interface UserServiceMapper {

    User toDomain(SignUpRequest request, String passwordEncrypt, String jwt);

    SignUpResponse toResponse(User user);

    LoginResponse toLoginResponse(User user, String newJwt);

}