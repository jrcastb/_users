package com.bci.exception;

import com.bci.exception.messages.BusinessErrorMessage;
import com.bci.exception.messages.TechnicalErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorModel> handleBusinessException(BusinessException ex) {
        ErrorModel errorModel = ErrorModel.builder()
                .timestamp(ex.getBusinessErrorMessage().getTimestamp())
                .codigo(ex.getBusinessErrorMessage().getCodigo())
                .detail(ex.getBusinessErrorMessage().getDetail())
                .build();
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TechnicalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorModel> handleTechnicalException(TechnicalException ex) {
        ErrorModel errorModel = ErrorModel.builder()
                .timestamp(ex.getTechnicalErrorMessage().getTimestamp())
                .codigo(ex.getTechnicalErrorMessage().getCodigo())
                .detail(ex.getTechnicalErrorMessage().getDetail())
                .build();
        return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorModel> handleGenericException(Exception ex) {
        ErrorModel errorModel = ErrorModel.builder()
                .timestamp(TechnicalErrorMessage.UNEXPECTED_ERROR.getTimestamp())
                .codigo(TechnicalErrorMessage.UNEXPECTED_ERROR.getCodigo())
                .detail(TechnicalErrorMessage.UNEXPECTED_ERROR.getDetail())
                .build();
        return new ResponseEntity<>(errorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorModel> handleSpringExceptions(MethodArgumentNotValidException ex) {
        ErrorModel errorModel = ErrorModel.builder()
                .timestamp(BusinessErrorMessage.BAD_REQUEST_BODY.getTimestamp())
                .codigo(BusinessErrorMessage.BAD_REQUEST_BODY.getCodigo())
                .detail(BusinessErrorMessage.BAD_REQUEST_BODY.getDetail())
                .build();
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorModel> handleSpringsExceptions(HttpRequestMethodNotSupportedException ex) {
        ErrorModel errorModel = ErrorModel.builder()
                .timestamp(BusinessErrorMessage.BAD_REQUEST_BODY.getTimestamp())
                .codigo(BusinessErrorMessage.BAD_REQUEST_BODY.getCodigo())
                .detail(BusinessErrorMessage.BAD_REQUEST_BODY.getDetail())
                .build();
        return new ResponseEntity<>(errorModel, HttpStatus.BAD_REQUEST);
    }
}