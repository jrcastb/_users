package com.bci.infrastructure.output.adapter

import com.bci.application.output.port.UserDb
import com.bci.infrastructure.output.repository.UserRepository
import com.bci.infrastructure.output.repository.entity.UserData
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
@RequiredArgsConstructor
class UserAdapterRepository implements UserDb {

    private final UserRepository repository;

    @Override
    Optional<UserData> findByEmail(String email) {
        try{
            return repository.findByEmail(email)
        }catch (Exception e){
            throw new Exception(e)//TechnicalException(e, TechnicalErrorMessage.USER_FIND_ONE)
        }
    }

    @Override
    void updateTokenAndLastLogin(String token, LocalDate lastLogin, String email) {
        try{
            repository.updateTokenAndLastLoginByEmail(token, lastLogin, email)
        }catch (Exception e){
            throw new Exception(e)//TechnicalException(e, TechnicalErrorMessage.UPDATE_TOKEN_AND_LAST_LOGIN)
        }
    }
}
