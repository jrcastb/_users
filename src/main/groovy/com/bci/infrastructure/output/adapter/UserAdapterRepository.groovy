package com.bci.infrastructure.output.adapter

import com.bci.application.output.port.UserDb
import com.bci.domain.User
import com.bci.infrastructure.exception.TechnicalException
import com.bci.infrastructure.exception.messages.TechnicalErrorMessage
import com.bci.infrastructure.output.adapter.mapper.UserMapper
import com.bci.infrastructure.output.repository.UserRepository
import com.bci.infrastructure.output.repository.entity.UserData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.time.LocalDate

@Component
class UserAdapterRepository implements UserDb {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Autowired
    UserAdapterRepository(UserRepository userRepository, UserMapper mapper) {
        this.repository = userRepository
        this.mapper = mapper
    }

    @Override
    Optional<User> findByEmail(String email) {
        try{
            return repository.findByEmail(email)
                    .map { userData -> mapper.toDomain(userData) }
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
