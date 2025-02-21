package com.bci.domain

import groovy.transform.Canonical
import groovy.transform.builder.Builder

@Builder
@Canonical
class SignUpRequest {
    String name
    String email
    String password
    List<Phone> phones
}
