package com.bci.infrastructure.exception.messages

import org.springframework.http.HttpStatus

import java.sql.Timestamp

enum TechnicalErrorMessage {
    SERVICE_NOT_FOUND(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Service Not Found"),
    UNEXPECTED_EXCEPTION(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Unexpected exception"),
    UPDATE_TOKEN_AND_LAST_LOGIN(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Error actualizando el token"),
    USER_FIND_ONE(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Error consultando el usuario")

    final Timestamp timeStamp;
    final int codigo;
    final String detail;

    TechnicalErrorMessage(Timestamp timestamp, int codigo, String detail){
        this.timeStamp = timestamp
        this.codigo = codigo
        this.detail = detail
    }
}