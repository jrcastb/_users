package com.bci.infrastructure.output.adapter.mapper

import com.bci.domain.SignUpRequest
import com.bci.domain.User
import com.bci.infrastructure.output.repository.entity.UserData

//@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
//@Mapper(componentModel = "spring")
interface UserMapper {

    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class)

    //@Mapping(target = "userId", source = "user.id")
    UserData toEntity(User user)
    //@Mapping(target = "id", source = "userData.userId")
    User toDomain(UserData userData)


}