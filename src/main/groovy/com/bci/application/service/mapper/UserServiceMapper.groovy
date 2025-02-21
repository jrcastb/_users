package com.bci.application.service.mapper

import com.bci.domain.SignUpRequest
import com.bci.domain.SignUpResponse
import com.bci.domain.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingConstants
import org.mapstruct.ReportingPolicy

import java.time.LocalDate
import java.time.LocalDateTime

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserServiceMapper {

    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "email", source = "request.email")
    @Mapping(target = "password", source = "passwordEncrypt")
    @Mapping(target = "created", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "token", source = "jwt")
    @Mapping(target = "password", defaultValue = "true")
    @Mapping(target = "phones", source = "request.phones")
    User toDomain(SignUpRequest request, String passwordEncrypt, String jwt)

    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "created", source = "user.created")
    @Mapping(target = "lastLogin", source = "user.lastLogin")
    @Mapping(target = "token", source = "user.token")
    @Mapping(target = "isActive", source = "user.isActive")
    SignUpResponse toResponse(User user)

}