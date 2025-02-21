package com.bci.infrastructure.exception.messages

import org.springframework.http.HttpStatus

import java.sql.Timestamp

enum BusinessErrorMessage {

    BAD_REQUEST_BODY(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Error in body request"),
    USER_NOT_FOUND(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "User not found")

    final Timestamp timeStamp;
    final int codigo;
    final String detail;

    BusinessErrorMessage(Timestamp timestamp, int codigo, String detail){
        this.timeStamp = timestamp
        this.codigo = codigo
        this.detail = detail
    }
}