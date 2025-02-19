package com.bci.application.output.port

import com.bci.infrastructure.output.repository.entity.UserData

import java.time.LocalDate

interface UserDb {

    Optional<UserData> findByEmail(String email);
    void updateTokenAndLastLogin(String token, LocalDate lastLogin, String email);

}