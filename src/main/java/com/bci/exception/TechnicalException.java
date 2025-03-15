package com.bci.exception;

import com.bci.exception.messages.TechnicalErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TechnicalException extends RuntimeException {

    private final TechnicalErrorMessage technicalErrorMessage;

    public TechnicalException(Throwable cause, TechnicalErrorMessage technicalErrorMessage) {
        super(cause);
        this.technicalErrorMessage = technicalErrorMessage;
    }
}