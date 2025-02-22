package com.bci.infrastructure.exception.messages

import org.springframework.http.HttpStatus

import java.sql.Timestamp
import java.time.Instant

enum BusinessErrorMessage {
    BAD_REQUEST_BODY(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Error in body request"),
    USER_NOT_FOUND(new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(), "User not found"),
    INVALID_EMAIL(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Invalid email format");

    private final Timestamp timestamp;
    private final Integer codigo;
    private final String detail;

    BusinessErrorMessage(Timestamp timestamp, Integer codigo, String detail) {
        this.timestamp = timestamp;
        this.codigo = codigo;
        this.detail = detail;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDetail() {
        return detail;
    }
}