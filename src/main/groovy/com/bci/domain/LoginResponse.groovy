package com.bci.domain

import groovy.transform.Canonical
import groovy.transform.builder.Builder

import java.time.LocalDate

@Canonical
@Builder
class LoginResponse {
    UUID id
    LocalDate created
    LocalDate lastLogin
    String token
    boolean isActive
    String name
    String email
    String password
    List<Phone> phones
}
