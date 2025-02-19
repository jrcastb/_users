package com.bci.infrastructure.exception

import lombok.extern.log4j.Log4j2
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

import javax.servlet.http.HttpServletRequest

@Log4j2
@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorModel> handleExceptions(BusinessException ex,
        HttpServletRequest request){
        log.error(ex)
        return new ResponseEntity<>(getErrorResponses(ex, request), HttpStatus.BAD_REQUEST)

    }
}
