package com.bci.infrastructure.output.adapter.mapper

import com.bci.domain.SignUpRequest
import com.bci.domain.User
import com.bci.infrastructure.output.repository.entity.UserData
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants.ComponentModel
import org.mapstruct.ReportingPolicy
import org.mapstruct.factory.Mappers

//@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Mapper(componentModel = "spring")
interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class)

    @Mapping(target = "userId", source = "user.id")
    UserData toEntity(User user)
    @Mapping(target = "id", source = "userData.userId")
    User toDomain(UserData userData)



}