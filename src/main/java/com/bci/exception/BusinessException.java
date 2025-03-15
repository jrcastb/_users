package com.bci.exception;

import com.bci.exception.messages.BusinessErrorMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessException extends RuntimeException {

    private final BusinessErrorMessage businessErrorMessage;
}