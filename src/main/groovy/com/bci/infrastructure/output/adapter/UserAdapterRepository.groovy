package com.bci.infrastructure.output.adapter

import com.bci.application.output.port.UserDb
import com.bci.domain.User
import com.bci.infrastructure.exception.TechnicalException
import com.bci.infrastructure.exception.messages.TechnicalErrorMessage
import com.bci.infrastructure.output.repository.UserRepository
import com.bci.infrastructure.output.repository.entity.UserData
import com.bci.infrastructure.output.repository.mapper.UserMapper
import groovy.transform.Canonical
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
@Canonical
class UserAdapterRepository implements UserDb {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    Optional<User> findByEmail(String email) {
        try{
            return Optional.of(mapper.toDomain(repository.findByEmail(email).ifPresent {}))
        }catch (Exception e){
            throw new TechnicalException(e, TechnicalErrorMessage.USER_FIND_ONE)
        }
    }

    @Override
    void updateTokenAndLastLogin(String token, LocalDate lastLogin, String email) {
        try{
            repository.updateTokenAndLastLoginByEmail(token, lastLogin, email)
        }catch (Exception e){
            throw new TechnicalException(e, TechnicalErrorMessage.UPDATE_TOKEN_AND_LAST_LOGIN)
        }
    }

    @Override
    User save(User user) {
        try{
            return mapper.toDomain(repository.save(mapper.toEntity(user)))
        }catch (Exception e){
            throw new TechnicalException(e, TechnicalErrorMessage.UPDATE_TOKEN_AND_LAST_LOGIN)
        }
    }
}
