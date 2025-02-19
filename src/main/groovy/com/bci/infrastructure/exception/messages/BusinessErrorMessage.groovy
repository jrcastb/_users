package com.bci.infrastructure.exception.messages

import lombok.Getter
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus

import java.sql.Timestamp
import java.time.LocalDate

@Getter
@RequiredArgsConstructor
enum BusinessErrorMessage {

    BAD_REQUEST_BODY(LocalDate.now(), HttpStatus.BAD_REQUEST, "Error in body request"),
    USER_NOT_FOUND(LocalDate.now(), HttpStatus.BAD_REQUEST, "Error in body request")

    private final Timestamp timeStamp;
    private final Integer code;
    private final String detail;
}