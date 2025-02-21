package com.bci.infrastructure.exception

import groovy.transform.Canonical
import groovy.transform.builder.Builder

import java.sql.Timestamp

@Builder
@Canonical
class ErrorModel {
    private Timestamp timestamp;
    private int codigo;
    private String detail;
}


