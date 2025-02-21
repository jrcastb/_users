package com.bci.infrastructure.output.repository.mapper

import com.bci.domain.SignUpRequest
import com.bci.domain.User
import com.bci.infrastructure.output.repository.entity.UserData
import org.mapstruct.Mapper
import org.mapstruct.MappingConstants.ComponentModel
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper {

    UserData toEntity(User user)

    User toDomain(UserData userData)



}