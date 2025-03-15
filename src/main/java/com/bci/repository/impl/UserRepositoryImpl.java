package com.bci.repository.impl;

import com.bci.repository.UserDb;
import com.bci.domain.User;
import com.bci.exception.TechnicalException;
import com.bci.exception.messages.TechnicalErrorMessage;
import com.bci.repository.mapper.UserMapper;
import com.bci.repository.UserRepository;
import com.bci.repository.entity.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserDb {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findByEmail(String email) {
        try {
            return repository.findByEmail(email)
                    .map(mapper::toDomain);
        } catch (Exception e) {
            throw new TechnicalException(e, TechnicalErrorMessage.USER_FIND_ONE);
        }
    }

    @Override
    public void updateTokenAndLastLogin(String token, LocalDate lastLogin, String email) {
        try {
            repository.updateTokenAndLastLoginByEmail(token, lastLogin, email);
        } catch (Exception e) {
            throw new TechnicalException(e, TechnicalErrorMessage.UPDATE_TOKEN_AND_LAST_LOGIN);
        }
    }

    @Override
    public User save(User user) {
        try {
            UserData userData = mapper.toEntity(user);
            UserData savedUserData = repository.save(userData);
            return mapper.toDomain(savedUserData);
        } catch (Exception e) {
            throw new TechnicalException(e, TechnicalErrorMessage.UPDATE_TOKEN_AND_LAST_LOGIN);
        }
    }
}