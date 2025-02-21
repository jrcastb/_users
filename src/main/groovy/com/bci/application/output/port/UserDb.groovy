package com.bci.application.output.port

import com.bci.domain.User

import java.time.LocalDate

interface UserDb {

    Optional<User> findByEmail(String email);
    void updateTokenAndLastLogin(String token, LocalDate lastLogin, String email);
    User save(User userData)

}