package com.bci.infrastructure.exception

import com.bci.infrastructure.exception.messages.TechnicalErrorMessage
import lombok.AccessLevel
import lombok.experimental.FieldDefaults

@FieldDefaults(level = AccessLevel.PRIVATE)
class TechnicalException extends RuntimeException {
    final TechnicalErrorMessage technicalErrorMessage;

    TechnicalException(Throwable cause, TechnicalErrorMessage technicalErrorMessage){
        super(cause)
        this.technicalErrorMessage = technicalErrorMessage
    }

}
