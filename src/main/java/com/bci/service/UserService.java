package com.bci.service;

import com.bci.domain.dto.LoginResponse;
import com.bci.domain.dto.SignUpRequest;
import com.bci.domain.dto.SignUpResponse;

public interface UserService {

    SignUpResponse signUp(SignUpRequest request);

    LoginResponse login(String token);

}