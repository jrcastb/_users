package com.bci.domain

import groovy.transform.Canonical
import groovy.transform.builder.Builder

import java.time.LocalDate

@Canonical
@Builder
class SignUpResponse {
    UUID id
    LocalDate created
    LocalDate lastLogin
    String token
    boolean isActive
}
