package com.bci.infrastructure.output.adapter.mapper

import com.bci.domain.SignUpRequest
import com.bci.domain.User
import com.bci.infrastructure.output.repository.entity.UserData

interface UserMapper {

    UserData toEntity(User user)
    User toDomain(UserData userData)

}