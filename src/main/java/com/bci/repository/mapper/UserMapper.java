package com.bci.repository.mapper;


import com.bci.domain.User;
import com.bci.repository.entity.UserData;

public interface UserMapper {

    UserData toEntity(User user);
    User toDomain(UserData userData);

}