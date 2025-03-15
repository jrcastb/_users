package com.bci.repository;

import com.bci.domain.User;

import java.time.LocalDate;
import java.util.Optional;

public interface UserDb {

    Optional<User> findByEmail(String email);
    void updateTokenAndLastLogin(String token, LocalDate lastLogin, String email);
    User save(User userData);

}