package com.bci.exception.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public enum BusinessErrorMessage {

    BAD_REQUEST_BODY(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Error in body request"),
    USER_NOT_FOUND(new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(), "User not found"),
    INVALID_EMAIL(new Timestamp(System.currentTimeMillis()), HttpStatus.BAD_REQUEST.value(), "Invalid email format");

    private final Timestamp timestamp;
    private final Integer codigo;
    private final String detail;
}