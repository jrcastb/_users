package com.bci.domain

import com.bci.infrastructure.output.repository.entity.PhoneData
import groovy.transform.Canonical
import groovy.transform.builder.Builder

import java.time.LocalDateTime

@Canonical
@Builder
class User {

    UUID id;
    String name;
    String email;
    String password;
    List<PhoneData> phones;
    LocalDateTime created;
    LocalDateTime lastLogin;
    String token;
    boolean isActive;
}
