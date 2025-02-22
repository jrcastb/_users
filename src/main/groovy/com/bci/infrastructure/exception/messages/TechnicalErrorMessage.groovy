package com.bci.infrastructure.exception.messages

import org.springframework.http.HttpStatus

import java.sql.Timestamp

enum TechnicalErrorMessage {
    DATABASE_ERROR(new Timestamp(System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database error"),
    SERVICE_UNAVAILABLE(new Timestamp(System.currentTimeMillis()), HttpStatus.SERVICE_UNAVAILABLE.value(), "Service unavailable"),
    UNEXPECTED_ERROR(new Timestamp(System.currentTimeMillis()), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unexpected error"),
    SERVICE_NOT_FOUND(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Service Not Found"),
    UNEXPECTED_EXCEPTION(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Unexpected exception"),
    UPDATE_TOKEN_AND_LAST_LOGIN(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Error actualizando el token"),
    USER_FIND_ONE(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Error consultando el usuario")

    private final Timestamp timestamp;
    private final Integer codigo;
    private final String detail;

    TechnicalErrorMessage(Timestamp timestamp, Integer codigo, String detail) {
        this.timestamp = timestamp;
        this.codigo = codigo;
        this.detail = detail;
    }

    Timestamp getTimestamp() {
        return timestamp;
    }

    Integer getCodigo() {
        return codigo;
    }

    String getDetail() {
        return detail;
    }
}