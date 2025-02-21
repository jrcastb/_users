package com.bci.domain

import com.bci.infrastructure.output.repository.entity.PhoneData
import groovy.transform.Canonical
import groovy.transform.builder.Builder

import java.time.LocalDate

@Canonical
@Builder
class User {

    UUID id;
    String name;
    String email;
    String password;
    List<PhoneData> phones;
    LocalDate created;
    LocalDate lastLogin;
    String token;
    boolean isActive;
}
