package com.bci.domain

import groovy.transform.Canonical
import groovy.transform.builder.Builder

@Canonical
@Builder
class Phone {
    long number
    int cityCode
    String countryCode
}
