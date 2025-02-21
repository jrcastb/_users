package com.bci.application.input.port

import com.bci.domain.LoginResponse
import com.bci.domain.SignUpRequest
import com.bci.domain.SignUpResponse

interface UserApi {

    SignUpResponse signUp(SignUpRequest request)

    LoginResponse login(String token)

}