package com.bci.application.service.mapper

import com.bci.domain.LoginResponse
import com.bci.domain.SignUpRequest
import com.bci.domain.SignUpResponse
import com.bci.domain.User

interface UserServiceMapper {

    User toDomain(SignUpRequest request, String passwordEncrypt, String jwt)

    SignUpResponse toResponse(User user)

    LoginResponse toLoginResponse(User user, String newJwt)

}