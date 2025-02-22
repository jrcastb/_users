package com.bci.infrastructure.exception

import com.bci.infrastructure.exception.messages.BusinessErrorMessage
import com.bci.infrastructure.exception.messages.TechnicalErrorMessage
import lombok.extern.log4j.Log4j2
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus


@Log4j2
@ControllerAdvice
class GlobalExceptionHandler {



    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorModel> handleBusinessException(BusinessException ex) {
        ErrorModel ErrorModel = new ErrorModel();
        ErrorModel.setTimestamp(ex.getBusinessErrorMessage().getTimestamp());
        ErrorModel.setCodigo(ex.getBusinessErrorMessage().getCodigo());
        ErrorModel.setDetail(ex.getBusinessErrorMessage().getDetail());
        return new ResponseEntity<>(ErrorModel, HttpStatus.BAD_REQUEST);
    }

    // Manejo de TechnicalException
    @ExceptionHandler(TechnicalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorModel> handleTechnicalException(TechnicalException ex) {
        ErrorModel ErrorModel = new ErrorModel();
        ErrorModel.setTimestamp(ex.getTechnicalErrorMessage().getTimestamp());
        ErrorModel.setCodigo(ex.getTechnicalErrorMessage().getCodigo());
        ErrorModel.setDetail(ex.getTechnicalErrorMessage().getDetail());
        return new ResponseEntity<>(ErrorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejo de excepciones genéricas
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ResponseEntity<ErrorModel> handleGenericException(Exception ex) {
        ErrorModel ErrorModel = new ErrorModel();
        ErrorModel.setTimestamp(TechnicalErrorMessage.UNEXPECTED_ERROR.getTimestamp());
        ErrorModel.setCodigo(TechnicalErrorMessage.UNEXPECTED_ERROR.getCodigo());
        ErrorModel.setDetail(TechnicalErrorMessage.UNEXPECTED_ERROR.getDetail());
        return new ResponseEntity<>(ErrorModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejo de excepciones específicas de Spring
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorModel> handleSpringExceptions(Exception ex) {
        ErrorModel ErrorModel = new ErrorModel();
        ErrorModel.setTimestamp(BusinessErrorMessage.BAD_REQUEST_BODY.getTimestamp())
        ErrorModel.setCodigo(BusinessErrorMessage.BAD_REQUEST_BODY.getCodigo())
        ErrorModel.setDetail(BusinessErrorMessage.BAD_REQUEST_BODY.getDetail())
        return new ResponseEntity<>(ErrorModel, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorModel> handleSpringsExceptions(Exception ex) {
        ErrorModel ErrorModel = new ErrorModel();
        ErrorModel.setTimestamp(BusinessErrorMessage.BAD_REQUEST_BODY.getTimestamp())
        ErrorModel.setCodigo(BusinessErrorMessage.BAD_REQUEST_BODY.getCodigo())
        ErrorModel.setDetail(BusinessErrorMessage.BAD_REQUEST_BODY.getDetail())
        return new ResponseEntity<>(ErrorModel, HttpStatus.BAD_REQUEST)
    }
}
