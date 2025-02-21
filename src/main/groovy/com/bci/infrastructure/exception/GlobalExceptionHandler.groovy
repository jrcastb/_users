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
    ResponseEntity<ErrorModel> handleExceptions(BusinessException ex){
        return new ResponseEntity<>(getErrorResponses(ex), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(TechnicalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorModel> handleExceptions(TechnicalException ex){
        return new ResponseEntity<>(getErrorResponses(ex), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ErrorModel> handleExceptions(Exception ex){
        return new ResponseEntity<>(getErrorResponses(ex), HttpStatus.BAD_REQUEST)
    }

    private static ErrorModel getErrorResponses(BusinessException businessException){
        return ErrorModel.builder()
                .timestamp(businessException.businessErrorMessage.timeStamp)
                .codigo(businessException.businessErrorMessage.codigo)
                .detail(businessException.businessErrorMessage.detail)
                .build()
    }

    private static ErrorModel getErrorResponses(TechnicalException technicalException){
        return ErrorModel.builder()
                .timestamp(technicalException.technicalErrorMessage.timeStamp)
                .codigo(technicalException.technicalErrorMessage.codigo)
                .detail(technicalException.technicalErrorMessage.detail)
                .build()
    }

    private static ErrorModel getErrorResponses(Exception exception){
        ErrorModel errorResponse;
        try {
            throw exception
        } catch (HttpRequestMethodNotSupportedException e){
            errorResponse = ErrorModel.builder()
                    .timestamp(TechnicalErrorMessage.SERVICE_NOT_FOUND.timeStamp)
                    .codigo(TechnicalErrorMessage.SERVICE_NOT_FOUND.codigo)
                    .detail(TechnicalErrorMessage.SERVICE_NOT_FOUND.detail)
                    .build()
        } catch (MethodArgumentNotValidException e){
            errorResponse = ErrorModel.builder()
                    .timestamp(BusinessErrorMessage.BAD_REQUEST_BODY.timeStamp)
                    .codigo(BusinessErrorMessage.BAD_REQUEST_BODY.codigo)
                    .detail(BusinessErrorMessage.BAD_REQUEST_BODY.detail)
                    .build()
        } catch (Exception e){
            errorResponse = ErrorModel.builder()
                    .timestamp(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.timeStamp)
                    .codigo(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.codigo)
                    .detail(TechnicalErrorMessage.UNEXPECTED_EXCEPTION.detail)
                    .build()
        }
        return errorResponse
    }
}
