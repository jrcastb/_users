package com.bci.infrastructure.exception

import com.bci.infrastructure.exception.messages.BusinessErrorMessage
import lombok.AccessLevel
import lombok.experimental.FieldDefaults


@FieldDefaults(level = AccessLevel.PRIVATE)
class BusinessException extends RuntimeException {

    final BusinessErrorMessage businessErrorMessage;
    final String extra;

    public BusinessException(BusinessErrorMessage businessErrorMessage, String extra){
        this.businessErrorMessage = businessErrorMessage;
        this.extra = extra;
    }

    public BusinessException(BusinessErrorMessage businessErrorMessage){
        this.businessErrorMessage = businessErrorMessage;
        this.extra = "";
    }
}
